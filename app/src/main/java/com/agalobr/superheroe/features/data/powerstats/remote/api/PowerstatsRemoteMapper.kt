package com.agalobr.superheroe.features.data.powerstats.remote.api

import com.agalobr.superheroe.features.domain.Powerstats

fun PowerstatsApiModel.toDomain(): Powerstats {
    return Powerstats(
        this.intelligence, this.strength, this.speed, this.durability, this.power, this.combat
    )
}