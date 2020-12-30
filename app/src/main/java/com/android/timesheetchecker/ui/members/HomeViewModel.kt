package com.android.timesheetchecker.ui.members

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.timesheetchecker.data.database.model.UserLocal
import com.android.timesheetchecker.domain.GetUsersUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(getUsersUseCase: GetUsersUseCase) : ViewModel() {

    val users: LiveData<List<UserLocal>> = getUsersUseCase()
}