package com.agalobr.superheroe.features.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val apiService: ApiService
    private val baseUrl: String = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/"

    init {
        apiService = buildEndPoint()
    }


    private fun createRetrofitClient() = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun buildEndPoint() = createRetrofitClient().create(ApiService::class.java)

    suspend fun getBiography(heroId: Int) = apiService.getSuperHeroeBiography(heroId)

    suspend fun getWork(heroId: Int) = apiService.getSuperHeroeWork(heroId)

    suspend fun getSuperHeroes() = apiService.getSuperHeroes()

    suspend fun getSuperHeroeById(heroId: Int) = apiService.getSuperHeroeId(heroId)

    suspend fun getConnections(heroId: Int) = apiService.getSuperHeroeConnections(heroId)

    suspend fun getPowerstats(heroId: Int) = apiService.getSuperHeroePowerstats(heroId)
}