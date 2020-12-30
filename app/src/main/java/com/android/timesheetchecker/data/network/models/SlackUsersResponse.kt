package com.android.timesheetchecker.data.network.models

import com.google.gson.annotations.SerializedName

data class SlackUsersResponse(
    @SerializedName("members")
    val members: List<SlackUser>
)