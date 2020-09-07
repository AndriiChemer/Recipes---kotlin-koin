package com.artatech.inkbook.recipes.core.extentions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener


fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun ImageView.loadImageRounded(url: String) {
    Glide.with(this)
        .load(url)
        .transform(CenterCrop(), RoundedCorners(60))
        .into(this)
}

fun ImageView.loadImageListener(url: String, listener: RequestListener<Drawable>) {
    Glide.with(this)
        .load(url)
        .listener(listener)
        .transform(CenterCrop(), RoundedCorners(60))
        .into(this)
}

fun ImageView.loadRoundedImage(url: String, radius: Int, listener: RequestListener<Drawable>) {
    Glide.with(this)
        .load(url)
        .listener(listener)
        .transform(CenterCrop(), RoundedCorners(radius))
        .into(this)
}