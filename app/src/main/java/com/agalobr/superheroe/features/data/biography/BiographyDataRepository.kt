package com.agalobr.superheroe.features.data.biography

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.features.data.biography.local.XmlBiographyLocalDataSource
import com.agalobr.superheroe.features.data.biography.remote.BiographyRemoteDataRepository
import com.agalobr.superheroe.features.domain.Biography
import com.agalobr.superheroe.features.domain.BiographyRepository

class BiographyDataRepository(
    private val remoteBiographySource: BiographyRemoteDataRepository,
    private val localBiographySource: XmlBiographyLocalDataSource
) :
    BiographyRepository {
    override suspend fun getBiography(heroId: Int): Either<ErrorApp, Biography?> {

        val biography = localBiographySource.getBiographyById(heroId)
        return if (biography.isLeft() || biography.get() == null) {
            remoteBiographySource.getBiography(heroId).map { biographyRemote ->
                localBiographySource.saveBiographyById(heroId, biographyRemote)
                biographyRemote
            }
        } else {
            biography
        }
    }
}
