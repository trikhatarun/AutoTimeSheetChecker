package com.android.timesheetchecker.domain

import androidx.lifecycle.LiveData
import com.android.timesheetchecker.data.AppRepository
import com.android.timesheetchecker.data.database.model.UserLocal
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(): LiveData<List<UserLocal>> {
        return repository.getUsersLiveData()
    }

    suspend operator fun invoke(once: Boolean): List<UserLocal>? {
        return if (once) {
            repository.getUsersOnce()
        } else {
            null
        }
    }
}