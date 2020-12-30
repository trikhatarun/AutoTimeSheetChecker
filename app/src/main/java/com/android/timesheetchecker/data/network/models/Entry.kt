package com.android.timesheetchecker.data.network.models

import com.google.gson.annotations.SerializedName

data class Entry(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("hours")
    var hours: Double,
    @SerializedName("id")
    val id: Int,
    @SerializedName("notes")
    val notes: String,
    @SerializedName("task_id")
    val taskId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user_id")
    val userId: Int
)