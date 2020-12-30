package com.android.timesheetchecker.domain

import com.android.timesheetchecker.data.AppRepository
import com.android.timesheetchecker.data.network.models.Entry
import com.android.timesheetchecker.utils.Result
import com.android.timesheetchecker.utils.minus
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FetchDailyEntriesUseCase @Inject constructor(private val repository: AppRepository) {
    suspend operator fun invoke(id: Int): Result<List<Entry>> {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return repository.fetchEntries(
            mapOf(
                FetchEntriesUseCase.START_DATE to sdf.format(getStartDate()),
                FetchEntriesUseCase.END_DATE to sdf.format(getStartDate()),
                FetchEntriesUseCase.USER_ID to id
            )
        )
    }

    private fun getStartDate(): Date {
        val currentTime = Calendar.getInstance()
        return currentTime.time - 1
    }
}
