package com.android.timesheetchecker.domain

import com.android.timesheetchecker.data.AppRepository
import javax.inject.Inject

class ResetCompletedStatusUseCase @Inject constructor(private val repository: AppRepository) {

    suspend operator fun invoke() {
        repository.resetCompletedStatus()
    }
}