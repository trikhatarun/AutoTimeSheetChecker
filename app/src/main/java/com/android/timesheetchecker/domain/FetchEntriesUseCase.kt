package com.android.timesheetchecker.domain

import com.android.timesheetchecker.data.AppRepository
import com.android.timesheetchecker.data.network.models.Entry
import com.android.timesheetchecker.utils.Result
import com.android.timesheetchecker.utils.plus
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FetchEntriesUseCase @Inject constructor(private val repository: AppRepository) {

    suspend operator fun invoke(id: Int): Result<List<Entry>> {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return repository.fetchEntries(
            mapOf(
                START_DATE to sdf.format(getStartDate()),
                END_DATE to sdf.format(getStartDate() + 6),
                USER_ID to id
            )
        )
    }

    private fun getStartDate(): Date {
        val startDate = Calendar.getInstance()
        startDate.set(Calendar.DAY_OF_WEEK, startDate.firstDayOfWeek + 1)
        val currentWeek = startDate.get(Calendar.WEEK_OF_YEAR)
        startDate.set(Calendar.WEEK_OF_YEAR, currentWeek - 1)
        return startDate.time
    }

    companion object {
        const val START_DATE = "start_date"
        const val END_DATE = "end_date"
        const val USER_ID = "user_id"
    }
}