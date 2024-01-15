package com.agalobr.superheroe.features.data.superheroe

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
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
            remoteSuperHeroeSource.getSuperHeroe().map {superHeroRemote->
                localSuperHeroSource.saveAll(superHeroRemote)
                superHeroRemote
            }
        }else{
            listSuperHeroe
        }
    }
}