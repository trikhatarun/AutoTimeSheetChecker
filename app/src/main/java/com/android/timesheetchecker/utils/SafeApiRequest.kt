package com.android.timesheetchecker.utils

import android.content.Context
import com.android.timesheetchecker.R
import com.android.timesheetchecker.utils.Constants.NO_INTERNET
import com.android.timesheetchecker.utils.Constants.SOMETHING_WENT_WRONG
import com.android.timesheetchecker.utils.Result
import java.io.IOException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest(private val context: Context) {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful)
                Result.Success(response.body()!!)
            else
                response.getErrorObject(context)
        } catch (e: IOException) {
            Result.Error(NO_INTERNET, context.getString(R.string.internet_error))
        } catch (e: Exception) {
            Result.Error(SOMETHING_WENT_WRONG, context.getString(R.string.something_went_wrong))
        }
    }
}

@Throws(Exception::class)
fun <T : Any> Response<T>.getErrorObject(context: Context): Result.Error {
    val error = errorBody()?.string()
    val message = StringBuilder()
    error?.let {
        try {
            message.append(JSONObject(it).getString("message"))
        } catch (e: JSONException) {
            message.append(context.getString(R.string.something_went_wrong))
        }
    }
    return Result.Error(code(), message.toString())
}
