package com.turastory.miniaccount

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun le(message: String) {
    Log.e("asdf", message)
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(msg: String) {
    context?.toast(msg)
}

fun Context.colorOf(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

const val FRAGMENT_CONTAINER = R.id.contentContainer

fun FragmentActivity.startFragment(fragment: Fragment, tag: String) {
    supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.fade_in_pop,
            R.anim.fade_out_pop
        )
        .replace(FRAGMENT_CONTAINER, fragment, tag)
        .addToBackStack(null)
        .commit()
}

// Assume that we always display Fragment on R.id.contentContainer
fun FragmentActivity.topFragment(): Fragment? =
    supportFragmentManager.findFragmentById(FRAGMENT_CONTAINER)

fun FragmentActivity.goBack() {
    supportFragmentManager.popBackStackImmediate()
}