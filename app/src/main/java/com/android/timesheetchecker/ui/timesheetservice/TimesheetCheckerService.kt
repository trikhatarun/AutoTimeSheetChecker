package com.android.timesheetchecker.ui.timesheetservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.android.timesheetchecker.R
import com.android.timesheetchecker.TimesheetCheckerApplication.Companion.NOTIFICATION_CHANNEL_ID
import com.android.timesheetchecker.data.database.model.UserLocal
import com.android.timesheetchecker.domain.*
import com.android.timesheetchecker.utils.Result
import com.android.timesheetchecker.utils.getTotal
import dagger.android.DaggerService
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import javax.inject.Inject

class TimesheetCheckerService : DaggerService() {

    @Inject
    lateinit var getUsersUseCase: GetUsersUseCase
    @Inject
    lateinit var entriesUseCase: FetchEntriesUseCase
    @Inject
    lateinit var sendSlackMessageUseCase: SendSlackMessageUseCase
    @Inject
    lateinit var markCompletedUseCase: MarkCompletedUseCase
    @Inject
    lateinit var sendConfirmationUseCase: SendConfirmationUseCase
    @Inject
    lateinit var resetCompletedStatusUseCase: ResetCompletedStatusUseCase

    private lateinit var job: Job

    private var slackMessage = ""
    private var warningHours = -1
    private var holidays = 0
    private var defaultedUsers = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        showNotificationAndStartForegroundService()

        intent?.let {
            holidays = it.getIntExtra(HOLIDAYS_KEY, 0)
            warningHours = 6.times(5 - holidays)
        }

        updateAndSendSlackMessage()

        return Service.START_REDELIVER_INTENT
    }

    private fun updateAndSendSlackMessage() {
        slackMessage = ""
        job = CoroutineScope(IO).launch {
            val usersList = getUsersUseCase(true)
            usersList?.forEach { user ->
                if (!user.excluded && !user.completed) {
                    fetchAndEvaluateUserEntries(user)
                }
            }

            withContext(Main) {
                if (defaultedUsers > 0) {
                    slackMessage += getString(R.string.slack_message_suffix)
                    sendSlackMessageUseCase(slackMessage)
                    stopSelf()
                } else {
                    sendConfirmationUseCase(getString(R.string.timesheet_completed))
                    resetCompletedStatusUseCase()
                    stopSelf()
                }
            }
        }
    }

    private suspend fun fetchAndEvaluateUserEntries(user: UserLocal) {
        entriesUseCase(user.id).let { entriesListResult ->
            when (entriesListResult) {
                is Result.Success -> {
                    val totalHours = entriesListResult.data.getTotal()
                    val entryDays =
                        entriesListResult.data.groupBy { it.date }.size
                    if (totalHours < warningHours || entryDays < 5 - holidays) {
                        addUserToSlackMessage(user)
                    } else {
                        markCompletedUseCase(user.id)
                    }
                }
                is Result.Error -> {
                    Toast.makeText(
                        this@TimesheetCheckerService,
                        entriesListResult.message,
                        Toast.LENGTH_LONG
                    ).show()
                    stopSelf()
                }
            }
        }
    }

    private fun addUserToSlackMessage(user: UserLocal) {
        defaultedUsers++
        if (slackMessage.isNotEmpty()) {
            slackMessage = slackMessage + ", " + getString(R.string.user_tag_string, user.slackId)
        } else {
            slackMessage += getString(R.string.user_tag_string, user.slackId)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    private fun showNotificationAndStartForegroundService() {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.foreground_service_message))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .build()

        startForeground(SERVICE_ID, notification)
    }

    companion object {
        const val SERVICE_ID = 101
        const val HOLIDAYS_KEY = "holidays"
    }
}
