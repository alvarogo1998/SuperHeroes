package com.agalobr.superheroe.features.data.connections

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.features.data.connections.local.XmlConnectionsLocalDataSource
import com.agalobr.superheroe.features.data.connections.remote.ConnectionsRemoteDataRepository
import com.agalobr.superheroe.features.domain.Connections
import com.agalobr.superheroe.features.domain.ConnectionsRepository

class ConnectionsDataRepository(
    private val localConnectionsRepository: XmlConnectionsLocalDataSource,
    private val remoteConnectionsRepository: ConnectionsRemoteDataRepository
) : ConnectionsRepository {
    override suspend fun getConnections(heroId: Int): Either<ErrorApp, Connections?> {
        val connectionsRemote = remoteConnectionsRepository.getConnections(heroId)
        val connectionsLocal = localConnectionsRepository.getConnectionsById(heroId)

        return if (connectionsRemote.isLeft() && connectionsLocal.get() != null) {
            connectionsLocal
        } else if (connectionsLocal.isLeft() || connectionsLocal.get() == null) {
            remoteConnectionsRepository.getConnections(heroId).map { connectionsRemote ->
                localConnectionsRepository.delete()
                localConnectionsRepository.saveConnectionsById(heroId, connectionsRemote)
                connectionsRemote
            }
        } else if (connectionsLocal.isLeft()) {
            ErrorApp.DatabaseErrorApp.left()
        } else if (connectionsRemote.isLeft()) {
            ErrorApp.InternetErrorApp.left()
        } else {
            connectionsRemote
        }
    }
}