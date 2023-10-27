package com.agalobr.superheroe.features.domain

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp

interface SuperHeroRepository {
    suspend fun getSuperHeroe(): Either<ErrorApp, List<SuperHeroe>>
}