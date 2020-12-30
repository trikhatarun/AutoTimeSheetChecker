package com.android.timesheetchecker.di

import androidx.lifecycle.ViewModelProvider
import com.android.timesheetchecker.TimesheetCheckerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: TimesheetCheckerViewModelFactory): ViewModelProvider.Factory
}
