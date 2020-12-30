package com.android.timesheetchecker.domain

import com.android.timesheetchecker.data.AppRepository
import com.android.timesheetchecker.data.database.model.UserLocal
import com.android.timesheetchecker.data.network.models.SlackUsersResponse
import com.android.timesheetchecker.data.network.models.User
import com.android.timesheetchecker.utils.Result
import javax.inject.Inject

class InitUseCase @Inject constructor(private val repository: AppRepository) {

    suspend operator fun invoke(): Result<Unit> {
        repository.getTickUser().let { tickUsers ->
            when (tickUsers) {
                is Result.Success -> {
                    repository.getSlackUser().let { slackUsers ->
                        return when (slackUsers) {
                            is Result.Success -> {
                                val localUserList = transformToLocalUsersList(slackUsers, tickUsers)
                                repository.storeUsers(localUserList)
                                Result.Success(Unit)
                            }
                            is Result.Error -> {
                                slackUsers
                            }
                        }
                    }
                }
                is Result.Error -> {
                    return tickUsers
                }
            }
        }
    }

    private fun transformToLocalUsersList(
        slackUsers: Result.Success<SlackUsersResponse>,
        tickUsers: Result.Success<List<User>>
    ): List<UserLocal> {
        val localUserList = mutableListOf<UserLocal>()

        for (tickUser in tickUsers.data) {
            tickUser.getSlackUser(slackUsers.data.members)?.let { matchingSlackUser ->
                localUserList.add(UserLocal(tickUser, matchingSlackUser))
            }
        }

        return localUserList
    }
}