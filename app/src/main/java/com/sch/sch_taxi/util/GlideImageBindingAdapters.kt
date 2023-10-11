package com.sch.sch_taxi.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@BindingAdapter("userProfile")
fun ImageView.bindUserProfile(profileUri: String?) {
    profileUri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(1000))
            .into(this)
    }
}
@BindingAdapter("glide10000")
fun ImageView.bindGlide10000(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(10000))
            .into(this)
    }
}

@BindingAdapter("glide1000")
fun ImageView.bindGlide1000(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(1000))
            .into(this)
    }
}