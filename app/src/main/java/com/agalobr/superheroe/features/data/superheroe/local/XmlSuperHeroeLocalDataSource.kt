package com.agalobr.superheroe.features.data.superheroe.local

import android.content.Context
import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.app.error.right
import com.agalobr.superheroe.app.extensions.JsonSerialization
import com.agalobr.superheroe.features.domain.SuperHeroe

class XmlSuperHeroeLocalDataSource(context: Context, private val serialization: JsonSerialization) {

    private val sharedPref = context.getSharedPreferences("superHero", Context.MODE_PRIVATE)

    fun saveAll(allHero: List<SuperHeroe>): Either<ErrorApp, List<SuperHeroe>> {
        return try {
            allHero.forEach { superHeroe ->
                sharedPref.edit().putString(
                    superHeroe.id.toString(),
                    serialization.toJson(superHeroe, SuperHeroe::class.java)
                )
                    .apply()
            }
            return allHero.right()
        } catch (ex: Exception) {
            ErrorApp.DatabaseErrorApp.left()
        }
    }

    fun getAllHero(): Either<ErrorApp, List<SuperHeroe>> {
        return try {
            val superHero: MutableList<SuperHeroe> = mutableListOf()
            val mapSuperHero = sharedPref.all as Map<String, String>
            mapSuperHero.values.map { jsonSuperHero ->
                superHero.add(serialization.fromJson(jsonSuperHero, SuperHeroe::class.java))
            }
            superHero.right()
        } catch (ex: Exception) {
            ErrorApp.DatabaseErrorApp.left()
        }
    }
}