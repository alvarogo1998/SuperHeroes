package com.agalobr.superheroe.features.data.biography.remote

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.features.domain.Biography

interface BiographyRemoteDataRepository {
    suspend fun getBiography(heroId: Int): Either<ErrorApp, Biography>
}