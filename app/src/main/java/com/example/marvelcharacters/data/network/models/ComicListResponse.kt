package com.example.marvelcharacters.data.network.models
import com.google.gson.annotations.SerializedName

data class ComicsList(
    @field:SerializedName("available") val available: Int,
    @field:SerializedName("returned") val returned: Int,
    @field:SerializedName("collectionURI") val collectionURI: String,
    @field:SerializedName("items") val items: List<CommicSummary>,
)