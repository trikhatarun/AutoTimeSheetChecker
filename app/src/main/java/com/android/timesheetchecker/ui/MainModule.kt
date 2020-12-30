package com.android.timesheetchecker.ui

import com.android.timesheetchecker.di.scopes.FragmentScoped
import com.android.timesheetchecker.ui.login.LoginFragment
import com.android.timesheetchecker.ui.login.LoginModule
import com.android.timesheetchecker.ui.members.HomeFragment
import com.android.timesheetchecker.ui.members.HomeModule
import com.android.timesheetchecker.ui.members.developers.DevTeamFragment
import com.android.timesheetchecker.ui.members.developers.DevTeamModule
import com.android.timesheetchecker.ui.membersdetail.MemberDetailFragment
import com.android.timesheetchecker.ui.membersdetail.MemberDetailModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun contributeLoginFragment(): LoginFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeFragment(): HomeFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [MemberDetailModule::class])
    abstract fun contributeMemberDetailFragment(): MemberDetailFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [DevTeamModule::class])
    abstract fun contributeDevTeamFragment(): DevTeamFragment
}