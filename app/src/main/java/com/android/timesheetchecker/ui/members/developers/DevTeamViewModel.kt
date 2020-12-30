package com.android.timesheetchecker.ui.members.developers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.timesheetchecker.data.database.model.UserLocal
import com.android.timesheetchecker.domain.GetDevsUseCase
import javax.inject.Inject

class DevTeamViewModel @Inject constructor(getDevsUseCase: GetDevsUseCase): ViewModel() {

    val users: LiveData<List<UserLocal>> = getDevsUseCase()
}