package com.agalobr.superheroe.features.data

import com.agalobr.superheroe.features.data.biography.remote.api.BiographyApiModel
import com.agalobr.superheroe.features.data.connections.remote.api.ConnectionsApiModel
import com.agalobr.superheroe.features.data.powerstats.remote.api.PowerstatsApiModel
import com.agalobr.superheroe.features.data.superheroe.remote.api.SuperHeroeApiModel
import com.agalobr.superheroe.features.data.work.remote.api.WorkApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("all.json")
    suspend fun getSuperHeroes(): Response<List<SuperHeroeApiModel>>

    @GET("id/{heroId}.json")
    suspend fun getSuperHeroeId(@Path("heroId") heroId: Int): Response<SuperHeroeApiModel>

    @GET("biography/{heroId}.json")
    suspend fun getSuperHeroeBiography(@Path("heroId") heroId: Int): Response<BiographyApiModel>

    @GET("work/{heroId}.json")
    suspend fun getSuperHeroeWork(@Path("heroId") heroId: Int): Response<WorkApiModel>

    @GET("connections/{heroId}.json")
    suspend fun getSuperHeroeConnections(@Path("heroId") heroId: Int): Response<ConnectionsApiModel>

    @GET("powerstats/{heroId}.json")
    suspend fun getSuperHeroePowerstats(@Path("heroId") heroId: Int): Response<PowerstatsApiModel>
}