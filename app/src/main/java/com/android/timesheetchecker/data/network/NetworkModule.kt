package com.android.timesheetchecker.data.network

import com.android.timesheetchecker.BuildConfig
import com.android.timesheetchecker.data.preferences.UserPreferences
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    //todo(add user agent: appname (email address))
    @Provides
    @Singleton
    fun provideHttpClient(preferences: UserPreferences): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor { chain ->
            val request = chain.request()
            val newRequest = if (preferences.authToken == null) {
                request.newBuilder()
                    /*.addHeader("User-Agent", "TickApp (email)")*/
                    .build()
            } else {
                request.newBuilder()
                    .addHeader("Authorization", "Token token=${preferences.authToken}")
                    /*.addHeader("User-Agent", "TickApp (email)")*/
                    .build()
            }
            chain.proceed(newRequest)
        }.build()
    }

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient): TickApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TICK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TickApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSlackWebHookApi(): SlackWebHookApi {
        return Retrofit.Builder()
            .baseUrl("https://hooks.slack.com/services/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SlackWebHookApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSlackApi(): SlackApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SLACK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SlackApi::class.java)
    }
}
