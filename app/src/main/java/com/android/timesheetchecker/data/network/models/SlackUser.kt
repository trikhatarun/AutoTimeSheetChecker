package com.android.timesheetchecker.data.network.models

import com.google.gson.annotations.SerializedName

data class SlackUser(
    @SerializedName("color")
    val color: String,
    @SerializedName("deleted")
    val deleted: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("is_admin")
    val isAdmin: Boolean,
    @SerializedName("is_app_user")
    val isAppUser: Boolean,
    @SerializedName("is_bot")
    val isBot: Boolean,
    @SerializedName("is_owner")
    val isOwner: Boolean,
    @SerializedName("is_primary_owner")
    val isPrimaryOwner: Boolean,
    @SerializedName("is_restricted")
    val isRestricted: Boolean,
    @SerializedName("is_ultra_restricted")
    val isUltraRestricted: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile")
    val profile: Profile,
    @SerializedName("real_name")
    val realName: String,
    @SerializedName("team_id")
    val teamId: String,
    @SerializedName("tz")
    val tz: Any,
    @SerializedName("tz_label")
    val tzLabel: String,
    @SerializedName("tz_offset")
    val tzOffset: Int,
    @SerializedName("updated")
    val updated: Int
)