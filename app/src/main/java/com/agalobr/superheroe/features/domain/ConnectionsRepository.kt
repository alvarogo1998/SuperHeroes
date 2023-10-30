package com.agalobr.superheroe.features.domain

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp

interface ConnectionsRepository {
    suspend fun getConnections(heroId: Int): Either<ErrorApp, Connections?>
}