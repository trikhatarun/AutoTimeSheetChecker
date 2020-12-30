package com.android.timesheetchecker.domain

import com.android.timesheetchecker.data.AppRepository
import com.android.timesheetchecker.utils.Result
import javax.inject.Inject

class SendConfirmationUseCase @Inject constructor(private val repository: AppRepository) {

    suspend operator fun invoke(message: String): Result<Unit> {
        return repository.sendConfirmationToPrai(message)
    }
}