package com.android.timesheetchecker.domain

import com.android.timesheetchecker.data.AppRepository
import com.android.timesheetchecker.utils.Result
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AppRepository) {

    suspend operator fun invoke(email: String, password: String, oauthToken: String): Result<Unit> {
        repository.login(email, password).let { loginResponse ->
            return when (loginResponse) {
                is Result.Success -> {
                    loginResponse.data.let { roles ->
                        if (roles.isNotEmpty()) {
                            val role = roles[0]
                            repository.setTokenAndSubscriptionId(role.apiToken, role.subscriptionId)
                            repository.setSlackOAuthToken(oauthToken)
                        }
                    }

                    Result.Success(Unit)
                }
                is Result.Error -> {
                    loginResponse
                }
            }
        }
    }

    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }

}