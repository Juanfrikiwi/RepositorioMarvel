package com.example.marvelcharacters.data.network.models

import com.google.gson.annotations.SerializedName
data class CommicSummary(
    @field:SerializedName("resource") val resource: String,
    @field:SerializedName("name") val name: String,
)