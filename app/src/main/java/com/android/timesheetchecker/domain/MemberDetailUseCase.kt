package com.android.timesheetchecker.domain

import androidx.lifecycle.LiveData
import com.android.timesheetchecker.data.AppRepository
import com.android.timesheetchecker.data.database.model.UserLocal
import javax.inject.Inject

class MemberDetailUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(id: Int): LiveData<UserLocal> {
        return repository.getUserById(id)
    }
}