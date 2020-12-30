package com.android.timesheetchecker.ui.membersdetail

import androidx.lifecycle.ViewModel
import com.android.timesheetchecker.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MemberDetailModule {

    @Binds
    @IntoMap
    @ViewModelKey(MemberDetailViewModel::class)
    abstract fun bindMemberDetailViewModel(memberDetailViewModel: MemberDetailViewModel): ViewModel
}
