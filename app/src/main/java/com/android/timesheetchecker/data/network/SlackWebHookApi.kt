package com.android.timesheetchecker.data.network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface SlackWebHookApi {
    //todo(add web hook for chat channel which contains all the users to send reminders)
    @POST("")
    suspend fun sendMessageToChannel(@Body message: JsonObject): Response<Unit>
    //todo(add web hook for chat channel with the manager who needs confirmation of timesheets)
    @POST("")
    suspend fun sendSuccessMessage(@Body message: JsonObject): Response<Unit>
}