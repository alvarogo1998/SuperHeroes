package com.agalobr.superheroe.features.data.powerstats.remote.api

import com.google.gson.annotations.SerializedName

data class PowerstatsApiModel(
    @SerializedName("intelligence") val intelligence: Int,
    @SerializedName("strength") val strength: Int,
    @SerializedName("speed") val speed: Int,
    @SerializedName("durability") val durability: Int,
    @SerializedName("power") val power: Int,
    @SerializedName("combat") val combat: Int,
)