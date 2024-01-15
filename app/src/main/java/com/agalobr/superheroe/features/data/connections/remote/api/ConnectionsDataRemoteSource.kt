package com.agalobr.superheroe.features.data.connections.remote.api

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.app.error.right
import com.agalobr.superheroe.features.data.ApiClient
import com.agalobr.superheroe.features.data.connections.remote.ConnectionsRemoteDataRepository
import com.agalobr.superheroe.features.domain.Connections

class ConnectionsDataRemoteSource(private val apiClient: ApiClient) :
    ConnectionsRemoteDataRepository {
    override suspend fun getConnections(heroId: Int): Either<ErrorApp, Connections> {
        return try {
            if (apiClient.getConnections(heroId).isSuccessful) {
                apiClient.getConnections(heroId).body()!!.toDomain().right()
            } else {
                ErrorApp.InternetErrorApp.left()
            }
        } catch (ex: Exception) {
            ErrorApp.InternetErrorApp.left()
        }
    }
}