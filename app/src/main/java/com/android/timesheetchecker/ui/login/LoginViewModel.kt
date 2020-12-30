package com.android.timesheetchecker.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.timesheetchecker.domain.InitUseCase
import com.android.timesheetchecker.domain.LoginUseCase
import com.android.timesheetchecker.utils.Event
import com.android.timesheetchecker.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val initUseCase: InitUseCase
) : ViewModel() {

    val loginSuccessEvent = MutableLiveData<Event<Unit>>()
    val errorEvent = MutableLiveData<Event<Result.Error>>()
    val loading = MutableLiveData<Event<Boolean>>()

    fun login(email: String, password: String, oauthToken: String) = viewModelScope.launch {
        loading.postValue(Event(true))
        loginUseCase(email, password, oauthToken).let {
            when (it) {
                is Result.Success -> {
                    initUseCase().let { initializeProcess ->
                        when (initializeProcess) {
                            is Result.Success -> {
                                loading.postValue(Event(false))
                                loginSuccessEvent.postValue(Event(Unit))
                            }
                            is Result.Error -> {
                                loading.postValue(Event(false))
                                errorEvent.postValue(Event(initializeProcess))
                            }
                        }
                    }
                }
                is Result.Error -> {
                    loading.postValue(Event(false))
                    errorEvent.postValue(Event(it))
                }
            }
        }
    }

    fun isLoggedIn(): Boolean {
        return loginUseCase.isLoggedIn()
    }
}