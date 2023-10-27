package com.agalobr.superheroe.features.domain

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp

class SuperHeroesFeedUseCase(
    private val workRepository: WorkRepository,
    private val biographyRepository: BiographyRepository,
    private val superHeroeRepository: SuperHeroRepository,
) {

    suspend operator fun invoke(): Either<ErrorApp, List<SuperHeroeList>> {
        val superHeroes = superHeroeRepository.getSuperHeroe()
        val list = superHeroes.map { superHeroeList ->
            superHeroeList.map { superHero ->
                val biography = biographyRepository.getBiography(superHero.id).get()
                val work = workRepository.getWork(superHero.id).get()

                SuperHeroeList(
                    superHero.id,
                    superHero.name,
                    superHero.getUrlImageM(),
                    biography!!.fullName,
                    work!!.occupation
                )
            }
        }
        return list
    }


    data class SuperHeroeList(
        val id: Int,
        val name: String,
        val image: String,
        val fullName: String,
        val occupation: String
    )
}