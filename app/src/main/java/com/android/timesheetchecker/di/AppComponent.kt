package com.android.timesheetchecker.di

import com.android.timesheetchecker.TimesheetCheckerApplication
import com.android.timesheetchecker.data.database.DatabaseModule
import com.android.timesheetchecker.data.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, NetworkModule::class, BindingModule::class, ViewModelModule::class, DatabaseModule::class])
interface AppComponent : AndroidInjector<TimesheetCheckerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TimesheetCheckerApplication): Builder

        fun build(): AppComponent
    }
}
