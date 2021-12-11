package com.example.marvelcharacters.utilities


import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.marvelcharacters.R
import dagger.hilt.android.qualifiers.ApplicationContext

object ImageUtils {
    fun loadImage(context:Context,thumbnail:String, imageView:ImageView){
        Glide.with(context).load(
            thumbnail.replace(
                context.getString(R.string.http_string),
                context.getString(R.string.https_string)
            )
        ).into(imageView)
    }

}
