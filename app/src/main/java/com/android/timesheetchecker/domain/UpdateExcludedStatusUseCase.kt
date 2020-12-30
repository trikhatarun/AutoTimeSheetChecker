package com.android.timesheetchecker.domain

import com.android.timesheetchecker.data.AppRepository
import javax.inject.Inject

class UpdateExcludedStatusUseCase @Inject constructor(private val repository: AppRepository) {

    suspend operator fun invoke(id: Int, excluded: Boolean) {
        repository.updateUserExcludedStatus(id, excluded)
    }
}