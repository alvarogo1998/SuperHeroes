package com.agalobr.superheroe.features.data.biography.remote.api

import com.google.gson.annotations.SerializedName

data class BiographyApiModel(
    @SerializedName("fullName") val fullName: String,
)