package com.agalobr.superheroe.features.data.connections.remote

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.features.domain.Connections

interface ConnectionsRemoteDataRepository {
    suspend fun getConnections(heroId: Int): Either<ErrorApp, Connections>
}