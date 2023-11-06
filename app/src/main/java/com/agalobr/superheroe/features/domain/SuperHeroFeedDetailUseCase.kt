package com.agalobr.superheroe.features.domain

import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.right

class SuperHeroFeedDetailUseCase(
    private val superHeroeRepository: SuperHeroRepository,
    private val biographyRepository: BiographyRepository,
    private val connectionsRepository: ConnectionsRepository,
    private val powerstatsRepository: PowerstatsRepository,
) {

    suspend operator fun invoke(heroId: Int): Either<ErrorApp, SuperHeroeDetail> {
        val superHero = superHeroeRepository.getSuperHeroById(heroId).get()
        val biography = biographyRepository.getBiography(heroId).get()
        val connections = connectionsRepository.getConnections(heroId).get()
        val powerstats = powerstatsRepository.getPowerstats(heroId).get()

        return SuperHeroeDetail(
            superHero!!.name,
            biography!!.fullName,
            biography.alignment,
            powerstats!!.intelligence,
            powerstats.speed,
            powerstats.combat,
            connections!!.groupAffiliation,
            superHero.getUrlImageXL(),
            superHero.urlImages
        ).right()
    }

    data class SuperHeroeDetail(
        val name: String,
        val fullName: String,
        val alignment: String,
        val intelligence: Int,
        val speed: Int,
        val combat: Int,
        val groupAffiliation: String,
        val imageMain: String,
        val images: List<String>
    )
}