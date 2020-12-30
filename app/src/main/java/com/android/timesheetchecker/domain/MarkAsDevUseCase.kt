package com.android.timesheetchecker.domain

import com.android.timesheetchecker.data.AppRepository
import javax.inject.Inject

class MarkAsDevUseCase @Inject constructor(private val repository: AppRepository) {

    suspend operator fun invoke(id: Int, isDev: Boolean) {
        repository.updateUserDevTeamStatus(id, isDev)
    }
}