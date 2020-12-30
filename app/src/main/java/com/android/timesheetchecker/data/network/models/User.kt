package com.android.timesheetchecker.data.network.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("billable_rate")
    val billableRate: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("updated_at")
    val updatedAt: String
) {
    var entries: List<Entry>? = null

    val hours: Double
        get() {
            var hours = 0.0
            entries?.forEach {
                hours += it.hours
            }
            return hours
        }

    fun getSlackUser(slackUsers: List<SlackUser>) =
        slackUsers.find { slackUser ->
            slackUser.profile.email == email
        }
}