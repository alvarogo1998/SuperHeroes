package com.agalobr.superheroe.features.domain

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp

interface BiographyRepository {
    suspend fun getBiography(heroId: Int): Either<ErrorApp, Biography?>
}