package com.agalobr.superheroe.features.data.powerstats

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.features.data.powerstats.local.XmlPowerstatsLocalDataSource
import com.agalobr.superheroe.features.data.powerstats.remote.PowerstatsRemoteDataRepository
import com.agalobr.superheroe.features.domain.Powerstats
import com.agalobr.superheroe.features.domain.PowerstatsRepository

class PowerstatsDataRepository(
    private val localPowerstatsRepository: XmlPowerstatsLocalDataSource,
    private val remotePowerstatsRepostiry: PowerstatsRemoteDataRepository
) : PowerstatsRepository {
    override suspend fun getPowerstats(heroId: Int): Either<ErrorApp, Powerstats?> {

        val powerstatsRemote = remotePowerstatsRepostiry.getPowerstats(heroId)
        val powerstatsLocal = localPowerstatsRepository.getPowerstatsById(heroId)
        return if (powerstatsRemote.isLeft() && powerstatsLocal.get() != null) {
            powerstatsLocal
        } else if (powerstatsLocal.isLeft() || powerstatsLocal.get() == null) {
            remotePowerstatsRepostiry.getPowerstats(heroId).map { powerstatsRemote ->
                localPowerstatsRepository.savePowerstatsById(heroId, powerstatsRemote)
                powerstatsRemote
            }
        } else if (powerstatsLocal.isLeft()) {
            ErrorApp.DatabaseErrorApp.left()
        } else if (powerstatsRemote.isLeft()) {
            ErrorApp.InternetErrorApp.left()
        } else {
            powerstatsRemote
        }
    }


}