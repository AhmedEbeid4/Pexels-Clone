package com.ebeid.wallpapersapp.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.hide() {
    if (this.visibility != View.GONE) { this.visibility = View.GONE }
}

fun View.show() {
    if (this.visibility != View.VISIBLE) { this.visibility = View.VISIBLE }
}

fun Fragment.toast(msg: String?) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
}