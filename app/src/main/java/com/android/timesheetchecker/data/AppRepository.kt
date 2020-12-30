package com.android.timesheetchecker.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.android.timesheetchecker.data.database.AppDatabase
import com.android.timesheetchecker.data.database.model.UserLocal
import com.android.timesheetchecker.data.network.SlackApi
import com.android.timesheetchecker.data.network.SlackWebHookApi
import com.android.timesheetchecker.data.network.TickApi
import com.android.timesheetchecker.data.network.models.SlackUsersResponse
import com.android.timesheetchecker.data.network.models.User
import com.android.timesheetchecker.data.preferences.UserPreferences
import com.android.timesheetchecker.utils.Result
import com.android.timesheetchecker.utils.SafeApiRequest
import com.android.timesheetchecker.utils.toBase64
import com.google.gson.JsonObject
import javax.inject.Inject

class AppRepository @Inject constructor(
    context: Context,
    private val tickApi: TickApi,
    private val slackApi: SlackApi,
    private val preferences: UserPreferences,
    private val appDatabase: AppDatabase,
    private val webHookApi: SlackWebHookApi
) : SafeApiRequest(context) {

    suspend fun login(email: String, password: String) = apiRequest {
        val base = "$email:$password"
        tickApi.getRoles("Basic " + base.toBase64())
    }

    fun setTokenAndSubscriptionId(authToken: String, subId: Int) {
        preferences.authToken = authToken
        preferences.subscriptionId = subId
    }

    fun setSlackOAuthToken(oauthToken: String) {
        preferences.slackOAuthToken = oauthToken
    }

    suspend fun getTickUser(): Result<List<User>> {
        return apiRequest {
            tickApi.getUsers(preferences.subscriptionId)
        }
    }

    suspend fun getSlackUser(): Result<SlackUsersResponse> {
        return apiRequest {
            slackApi.getUsers(preferences.slackOAuthToken)
        }
    }

    suspend fun storeUsers(users: List<UserLocal>) {
        val excludedUsers = appDatabase.userLocalDao().getExcludedUsersId()
        users.forEach { user ->
            if (excludedUsers.contains(user.id)) {
                user.excluded = true
            }
        }

        appDatabase.userLocalDao().insertAllUsers(users)
    }

    fun getUsersLiveData(): LiveData<List<UserLocal>> {
        return appDatabase.userLocalDao().getAllUsersLiveData()
    }

    fun getDevUsersLiveData() : LiveData<List<UserLocal>> {
        return appDatabase.userLocalDao().getDevUsersLiveData()
    }

    fun isLoggedIn(): Boolean {
        return preferences.authToken != null && preferences.slackOAuthToken.isNotEmpty() && preferences.subscriptionId != -1
    }

    suspend fun sendSlackMessage(message: String): Result<Unit> {
        return apiRequest {
            val jsonMessageBody = JsonObject()
            jsonMessageBody.addProperty("text", message)
            webHookApi.sendMessageToChannel(jsonMessageBody)
        }
    }

    suspend fun updateUserExcludedStatus(id: Int, excluded: Boolean) {
        appDatabase.userLocalDao().updateUserExcludedStatus(id, excluded)
    }

    suspend fun updateUserDevTeamStatus(id: Int, isDev: Boolean) {
        appDatabase.userLocalDao().updateUserDevTeamStatus(id, isDev)
    }

    fun getUserById(id: Int): LiveData<UserLocal> {
        return appDatabase.userLocalDao().getUserById(id)
    }

    suspend fun fetchEntries(queries: Map<String, Any>) = apiRequest {
        tickApi.getEntries(preferences.subscriptionId, queries)
    }

    suspend fun markUserTimesheetCompleted(id: Int) {
        appDatabase.userLocalDao().updateUserCompletedStatus(id)
    }

    suspend fun sendConfirmationToPrai(message: String): Result<Unit> {
        return apiRequest {
            val jsonMessageBody = JsonObject()
            jsonMessageBody.addProperty("text", message)
            webHookApi.sendSuccessMessage(jsonMessageBody)
        }
    }

    suspend fun resetCompletedStatus() {
        appDatabase.userLocalDao().resetCompletedStatus()
    }

    suspend fun getUsersOnce(): List<UserLocal> {
        return appDatabase.userLocalDao().getAllUsersOnce()
    }

    suspend fun getDevUsersOnce(): List<UserLocal> {
        return appDatabase.userLocalDao().getDevUsersOnce()
    }

    fun markTimeSheetCompletedForDay(id: Int) {
        appDatabase.userLocalDao().updateUserCompletedForDayStatus(id)
    }
}