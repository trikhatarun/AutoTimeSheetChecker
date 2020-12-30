package com.android.timesheetchecker.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object DataBindingAdapters {

    @JvmStatic
    @BindingAdapter("showError", "errorMessage", requireAll = true)
    fun setError(input: TextInputLayout, enabled: Boolean, message: String) {
        input.error = message
        input.isErrorEnabled = enabled
    }
}
