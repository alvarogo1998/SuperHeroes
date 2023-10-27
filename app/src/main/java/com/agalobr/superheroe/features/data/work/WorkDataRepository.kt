package com.agalobr.superheroe.features.data.work

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.features.data.work.local.XmlWorkLocalDataSource
import com.agalobr.superheroe.features.data.work.remote.WorkRemoteDataRepository
import com.agalobr.superheroe.features.domain.Work
import com.agalobr.superheroe.features.domain.WorkRepository

class WorkDataRepository(
    private val localWorkSource: XmlWorkLocalDataSource,
    private val remoteWorkrSource: WorkRemoteDataRepository
) : WorkRepository {
    override suspend fun getWork(heroId: Int): Either<ErrorApp, Work?> {
        val work = localWorkSource.getWorkById(heroId)
        return if (work.isLeft() || work.get() == null) {
            remoteWorkrSource.getWork(heroId).map { workRemote ->
                localWorkSource.saveWorkById(heroId, workRemote)
                workRemote
            }
        } else {
            work
        }
    }
}