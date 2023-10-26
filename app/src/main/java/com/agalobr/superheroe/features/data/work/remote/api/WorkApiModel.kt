package com.agalobr.superheroe.features.data.work.remote.api

import com.google.gson.annotations.SerializedName

data class WorkApiModel(
    @SerializedName("occupation") val occupation: String,
)