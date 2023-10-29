package com.agalobr.superheroe.features.data.powerstats.remote

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.features.domain.Powerstats

interface PowerstatsRemoteDataRepository {
    suspend fun getPowerstats(heroId: Int): Either<ErrorApp, Powerstats>
}