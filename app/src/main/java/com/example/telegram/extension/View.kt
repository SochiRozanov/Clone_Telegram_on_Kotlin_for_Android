package com.example.telegram.extension

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.telegram.R

fun ImageView.setImage(imageUrl: String) {
    Glide
        .with(this.context)
        .load(imageUrl)
        .centerCrop()
        .placeholder(R.drawable.background)
        .into(this)
}

fun View.getPixelFromDp(paddingDp: Int): Float {
    val density = context.resources.displayMetrics.density
    return (paddingDp * density)
}

fun View.getDpFromPixel(paddingDp: Int): Float {
    val density = context.resources.displayMetrics.density
    return (paddingDp / density)
}