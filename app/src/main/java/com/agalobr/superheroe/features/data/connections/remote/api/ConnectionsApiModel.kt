package com.agalobr.superheroe.features.data.connections.remote.api

import com.google.gson.annotations.SerializedName

data class ConnectionsApiModel(
    @SerializedName("groupAffiliation") val groupAffiliation: String
)
