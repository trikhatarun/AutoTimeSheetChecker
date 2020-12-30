package com.android.timesheetchecker.data.network.models


import com.google.gson.annotations.SerializedName

data class Role(
    @SerializedName("api_token")
    val apiToken: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("subscription_id")
    val subscriptionId: Int
)