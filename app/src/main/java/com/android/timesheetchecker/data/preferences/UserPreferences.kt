package com.android.timesheetchecker.data.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import javax.inject.Inject

class UserPreferences @Inject constructor(context: Context) {
    private val preferenceManager by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    var authToken: String?
        get() = preferenceManager.getString(AUTH_TOKEN, null)
        set(value) = preferenceManager.edit().putString(AUTH_TOKEN, value).apply()

    var slackOAuthToken: String
        get() = preferenceManager.getString(SLACK_AUTH_TOKEN, null) ?: ""
        set(value) = preferenceManager.edit().putString(SLACK_AUTH_TOKEN, value).apply()

    var subscriptionId: Int
        get() = preferenceManager.getInt(SUBSCRIPTION_ID, -1)
        set(value) = preferenceManager.edit().putInt(SUBSCRIPTION_ID, value).apply()

    companion object {
        private const val AUTH_TOKEN = "auth_token"
        private const val SLACK_AUTH_TOKEN = "slack_auth_token"
        private const val SUBSCRIPTION_ID = "subscription_id"
    }
}
