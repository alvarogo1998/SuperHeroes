package com.agalobr.superheroe.features.domain

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp

interface WorkRepository {
    suspend fun getWork(heroId: Int): Either<ErrorApp, Work?>
}