package com.android.timesheetchecker.data.network.models

import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("always_active")
    val alwaysActive: Boolean,
    @SerializedName("avatar_hash")
    val avatarHash: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("display_name_normalized")
    val displayNameNormalized: String,
    @SerializedName("fields")
    val fields: Any,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("image_192")
    val image192: String,
    @SerializedName("image_24")
    val image24: String,
    @SerializedName("image_32")
    val image32: String,
    @SerializedName("image_48")
    val image48: String,
    @SerializedName("image_512")
    val image512: String,
    @SerializedName("image_72")
    val image72: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("real_name")
    val realName: String,
    @SerializedName("real_name_normalized")
    val realNameNormalized: String,
    @SerializedName("skype")
    val skype: String,
    @SerializedName("status_emoji")
    val statusEmoji: String,
    @SerializedName("status_expiration")
    val statusExpiration: Int,
    @SerializedName("status_text")
    val statusText: String,
    @SerializedName("status_text_canonical")
    val statusTextCanonical: String,
    @SerializedName("team")
    val team: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("email")
    val email: String
)