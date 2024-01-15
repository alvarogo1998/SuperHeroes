package com.agalobr.superheroe.features.data.superheroe.remote

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.features.domain.SuperHeroe

interface SuperHeroeRemoteDataRepository {
    suspend fun getSuperHeroe(): Either<ErrorApp, List<SuperHeroe>>
}