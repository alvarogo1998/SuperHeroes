package com.agalobr.superheroe.features.data.superheroe.remote.api

import com.agalobr.superheroe.features.domain.SuperHeroe

fun SuperHeroeApiModel.toDomain(): SuperHeroe {
    return SuperHeroe(
        this.id, this.name, listOf(
            this.image.xs, this.image.sm, this.image.md, this.image.lg
        )
    )
}

