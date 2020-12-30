package com.android.timesheetchecker.data.network.models

import com.google.gson.annotations.SerializedName

class SlackMessage(
    @SerializedName("text")
    val message: String
)
