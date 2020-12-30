package com.android.timesheetchecker.di

import com.android.timesheetchecker.di.scopes.ActivityScoped
import com.android.timesheetchecker.ui.MainActivity
import com.android.timesheetchecker.ui.MainModule
import com.android.timesheetchecker.ui.timesheetservice.DailyTimesheetCheckerService
import com.android.timesheetchecker.ui.timesheetservice.TimesheetCheckerService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeTimeSheetService(): TimesheetCheckerService

    @ContributesAndroidInjector
    abstract fun contributeDailyTimesheetService(): DailyTimesheetCheckerService
}
