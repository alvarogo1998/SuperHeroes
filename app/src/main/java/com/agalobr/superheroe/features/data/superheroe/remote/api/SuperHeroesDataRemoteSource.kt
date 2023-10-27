package com.agalobr.superheroe.features.data.superheroe.remote.api

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.app.error.right
import com.agalobr.superheroe.features.data.ApiClient
import com.agalobr.superheroe.features.data.superheroe.remote.SuperHeroeRemoteDataRepository
import com.agalobr.superheroe.features.domain.SuperHeroe

class SuperHeroesDataRemoteSource(private val apiClient: ApiClient) :
    SuperHeroeRemoteDataRepository {
    override suspend fun getSuperHeroe(): Either<ErrorApp, List<SuperHeroe>> {
        return try {
            if (apiClient.getSuperHeroes().isSuccessful) {
                apiClient.getSuperHeroes().body()!!.subList(0, 100).map { superHeroesApiModel ->
                    superHeroesApiModel.toDomain()
                }.right()
            } else {
                ErrorApp.InternetErrorApp.left()
            }
        } catch (ex: Exception) {
            ErrorApp.InternetErrorApp.left()
        }
    }
}