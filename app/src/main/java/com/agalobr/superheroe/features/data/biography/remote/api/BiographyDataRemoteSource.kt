package com.agalobr.superheroe.features.data.biography.remote.api

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.app.error.right
import com.agalobr.superheroe.features.data.ApiClient
import com.agalobr.superheroe.features.data.biography.remote.BiographyRemoteDataRepository
import com.agalobr.superheroe.features.domain.Biography

class BiographyDataRemoteSource(private val apiClient: ApiClient) :
    BiographyRemoteDataRepository {
    override suspend fun getBiography(heroId: Int): Either<ErrorApp, Biography> {

        return try {
            if (apiClient.getBiography(heroId).isSuccessful) {
                apiClient.getBiography(heroId).body()!!.toDomain().right()
            } else {
                ErrorApp.InternetErrorApp.left()
            }
        } catch (ex: Exception) {
            ErrorApp.InternetErrorApp.left()
        }
    }
}