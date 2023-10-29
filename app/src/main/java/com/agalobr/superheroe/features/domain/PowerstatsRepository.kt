package com.agalobr.superheroe.features.domain

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp

interface PowerstatsRepository {
    suspend fun getPowerstats(heroId: Int): Either<ErrorApp, Powerstats?>
}