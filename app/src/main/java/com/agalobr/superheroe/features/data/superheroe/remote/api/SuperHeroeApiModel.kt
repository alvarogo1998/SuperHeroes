package com.agalobr.superheroe.features.data.superheroe.remote.api

import com.google.gson.annotations.SerializedName

data class SuperHeroeApiModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("images") val image: Images
)

data class Images(
    @SerializedName("xs") val xs: String,
    @SerializedName("sm") val sm: String,
    @SerializedName("md") val md: String,
    @SerializedName("lg") val lg: String
)
