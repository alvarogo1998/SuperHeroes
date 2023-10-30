package com.agalobr.superheroe.features.data.biography.remote.api

import com.agalobr.superheroe.features.domain.Biography

fun BiographyApiModel.toDomain(): Biography {
    return Biography(this.fullName, this.alignment)
}