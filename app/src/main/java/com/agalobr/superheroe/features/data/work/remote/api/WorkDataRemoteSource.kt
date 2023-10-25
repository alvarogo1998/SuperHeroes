package com.agalobr.superheroe.features.data.work.remote.api

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.app.error.right
import com.agalobr.superheroe.features.data.ApiClient
import com.agalobr.superheroe.features.data.work.remote.WorkRemoteDataRepository
import com.agalobr.superheroe.features.domain.Work

class WorkDataRemoteSource(private val apiClient: ApiClient) : WorkRemoteDataRepository {
    override suspend fun getWork(heroId: Int): Either<ErrorApp, Work> {
        return try {
            if (apiClient.getWork(heroId).isSuccessful) {
                apiClient.getWork(heroId).body()!!.toDomain().right()
            } else {
                ErrorApp.InternetErrorApp.left()
            }
        } catch (ex: Exception) {
            ErrorApp.InternetErrorApp.left()
        }
    }
}