package com.android.timesheetchecker.domain

import com.android.timesheetchecker.data.AppRepository
import javax.inject.Inject

class MarkCompletedUseCase @Inject constructor(private val repository: AppRepository) {

    suspend operator fun invoke(id: Int) {
        repository.markUserTimesheetCompleted(id)
    }
}