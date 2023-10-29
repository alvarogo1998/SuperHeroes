package com.agalobr.superheroe.features.data.powerstats.remote.api

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.app.error.right
import com.agalobr.superheroe.features.data.ApiClient
import com.agalobr.superheroe.features.data.powerstats.remote.PowerstatsRemoteDataRepository
import com.agalobr.superheroe.features.domain.Powerstats

class PowerstatsDataRemoteSource(private val apiClient: ApiClient):PowerstatsRemoteDataRepository {
    override suspend fun getPowerstats(heroId: Int): Either<ErrorApp, Powerstats> {
        return try {
            if (apiClient.getPowerstats(heroId).isSuccessful) {
                apiClient.getPowerstats(heroId).body()!!.toDomain().right()
            } else {
                ErrorApp.InternetErrorApp.left()
            }
        } catch (ex: Exception) {
            ErrorApp.InternetErrorApp.left()
        }
    }
}