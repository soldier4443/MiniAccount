package com.turastory.miniaccount

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast

fun le(message: String) {
    Log.e("asdf", message)
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.colorOf(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)
