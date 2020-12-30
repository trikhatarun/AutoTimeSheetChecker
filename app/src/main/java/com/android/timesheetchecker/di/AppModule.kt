package com.android.timesheetchecker.di

import android.content.Context
import com.android.timesheetchecker.TimesheetCheckerApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: TimesheetCheckerApplication): Context {
        return application.applicationContext
    }
}
