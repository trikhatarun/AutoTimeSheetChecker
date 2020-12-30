package com.android.timesheetchecker.data.database.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.android.timesheetchecker.data.network.models.SlackUser
import com.android.timesheetchecker.data.network.models.User

@Entity
data class UserLocal(
    @PrimaryKey
    val id: Int,
    val email: String,
    val name: String,
    val slackId: String,
    var completed: Boolean,
    var excluded: Boolean,
    var isDev: Boolean,
    var completedForDay: Boolean
) {
    @Ignore
    constructor(tickUser: User, slackUser: SlackUser) : this(
        tickUser.id,
        tickUser.email,
        "${tickUser.firstName} ${tickUser.lastName}",
        slackUser.id,
        false,
        false,
        false,
        false
    )
}