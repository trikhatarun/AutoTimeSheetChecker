package com.android.timesheetchecker.data.network

import com.android.timesheetchecker.data.network.models.Entry
import com.android.timesheetchecker.data.network.models.Role
import com.android.timesheetchecker.data.network.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface TickApi {
    @GET("api/v2/roles.json")
    suspend fun getRoles(@Header("Authorization") authHeader: String): Response<List<Role>>

    @GET("{subscriptionId}/api/v2/users.json")
    suspend fun getUsers(
        @Path("subscriptionId") subscriptionId: Int
    ): Response<List<User>>

    @GET("{subscriptionId}/api/v2/entries.json")
    suspend fun getEntries(
        @Path("subscriptionId") subscriptionId: Int,
        @QueryMap queries: Map<String, @JvmSuppressWildcards Any>
    ): Response<List<Entry>>
}
