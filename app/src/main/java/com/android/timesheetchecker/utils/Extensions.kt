package com.android.timesheetchecker.utils

import android.content.Context
import android.util.Base64
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.timesheetchecker.data.network.models.Entry
import com.google.android.material.snackbar.Snackbar
import java.util.*

fun AppCompatActivity.snackBar(@StringRes message: Int) {
    Snackbar.make(window.decorView.rootView, getString(message), Snackbar.LENGTH_SHORT).show()
}

fun AppCompatActivity.snackBar(message: String?) {
    if (message != null)
        Snackbar.make(window.decorView.rootView, message, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.snackBar(@StringRes message: Int) {
    view?.let {
        Snackbar.make(it, getString(message), Snackbar.LENGTH_SHORT).show()
    }
}

fun Fragment.snackBar(message: String?) {
    if (message != null)
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
}


fun Context.toast(message: String?) {
    if (message != null)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun String.toBase64() = Base64.encodeToString(toByteArray(), Base64.NO_WRAP)

operator fun Date.plus(days: Int): Date {
    val oneDay = 86400000
    return Date(time + oneDay.times(days))
}

operator fun Date.minus(days: Int): Date {
    val oneDay = 86400000
    return Date(time - oneDay.times(days))
}

fun Spinner.onItemSelected(onItemSelected: (Int) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            onItemSelected.invoke(position)
        }
    }
}

fun List<Entry>.getTotal(): Double {
    var total = 0.0

    forEach {
        total += it.hours
    }

    return total
}
