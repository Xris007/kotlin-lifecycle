package com.noblecilla.timefighter

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlin.random.Random

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.blink() {
    startAnimation(AnimationUtils.loadAnimation(context, R.anim.blink))
}

fun View.random() {
    val displayMetrics = Resources.getSystem().displayMetrics
    x = Random.nextInt(0, displayMetrics.widthPixels - 250).toFloat()
    y = Random.nextInt(100, displayMetrics.heightPixels - 350).toFloat()
}
