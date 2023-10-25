package com.agalobr.superheroe.features.data.work.remote

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.features.domain.Work

interface WorkRemoteDataRepository {

    suspend fun getWork(heroId: Int): Either<ErrorApp, Work>
}