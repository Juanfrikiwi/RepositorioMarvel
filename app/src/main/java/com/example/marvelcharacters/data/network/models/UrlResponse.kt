package com.example.marvelcharacters.data.network.models

import com.google.gson.annotations.SerializedName

data class UrlResponse(
    @field:SerializedName("type") val type: String,
    @field:SerializedName("url") val url: String,
)
