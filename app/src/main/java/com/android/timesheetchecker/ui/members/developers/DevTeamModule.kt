package com.android.timesheetchecker.ui.members.developers

import androidx.lifecycle.ViewModel
import com.android.timesheetchecker.di.ViewModelKey
import com.android.timesheetchecker.ui.members.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DevTeamModule {

    @Binds
    @IntoMap
    @ViewModelKey(DevTeamViewModel::class)
    abstract fun bindDevTeamViewModel(devTeamViewModel: DevTeamViewModel): ViewModel
}