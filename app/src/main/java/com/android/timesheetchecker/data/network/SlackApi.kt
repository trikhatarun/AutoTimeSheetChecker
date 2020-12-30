package com.android.timesheetchecker.data.network

import com.android.timesheetchecker.data.network.models.SlackUsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SlackApi {
    @GET("users.list/")
    suspend fun getUsers(@Query("token") token: String): Response<SlackUsersResponse>
}