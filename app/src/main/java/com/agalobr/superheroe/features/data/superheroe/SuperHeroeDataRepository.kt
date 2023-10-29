package com.agalobr.superheroe.features.data.superheroe

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.features.data.superheroe.local.XmlSuperHeroeLocalDataSource
import com.agalobr.superheroe.features.data.superheroe.remote.SuperHeroeRemoteDataRepository
import com.agalobr.superheroe.features.domain.SuperHeroRepository
import com.agalobr.superheroe.features.domain.SuperHeroe

class SuperHeroeDataRepository(
    private val remoteSuperHeroeSource: SuperHeroeRemoteDataRepository,
    private val localSuperHeroSource: XmlSuperHeroeLocalDataSource
) : SuperHeroRepository {
    override suspend fun getSuperHeroe(): Either<ErrorApp, List<SuperHeroe>> {
        val listSuperHeroe = localSuperHeroSource.getAllHero()
        return if (listSuperHeroe.isLeft() || listSuperHeroe.get().isEmpty()) {
            remoteSuperHeroeSource.getSuperHeroe().map { superHeroRemote ->
                localSuperHeroSource.saveAll(superHeroRemote)
                superHeroRemote
            }
        } else {
            listSuperHeroe
        }
    }

    override suspend fun getSuperHeroById(heroId: Int): Either<ErrorApp, SuperHeroe?> {
        val superHeroRemote = remoteSuperHeroeSource.getSuperHeroById(heroId)
        val superHeroLocal = localSuperHeroSource.getHeroById(heroId)

        return if (superHeroRemote.isLeft() && superHeroLocal.get() != null) {
            superHeroLocal
        } else if (superHeroLocal.isLeft() || superHeroLocal.get() == null) {
            remoteSuperHeroeSource.getSuperHeroById(heroId).map { superHeroRemote ->
                superHeroRemote
            }
        } else if (superHeroLocal.isLeft()) {
            ErrorApp.DatabaseErrorApp.left()
        } else if (superHeroRemote.isLeft()) {
            ErrorApp.InternetErrorApp.left()
        } else {
            superHeroRemote
        }
    }
}