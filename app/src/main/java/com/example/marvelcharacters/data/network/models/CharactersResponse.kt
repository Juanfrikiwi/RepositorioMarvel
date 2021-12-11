package com.example.marvelcharacters.data.network.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class CharactersResponse(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("modified") val modified: Date,
    @field:SerializedName("resourceURI") val resourceURI: String,
    @field:SerializedName("urls") val urls: List<UrlResponse>,
    @field:SerializedName("thumbnail") val thumbnail: ImageResponse,
    @field:SerializedName("comics") val comics: ComicsList
)