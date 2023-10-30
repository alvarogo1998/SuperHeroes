package com.agalobr.superheroe.features.data.powerstats.local

import android.content.Context
import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.app.error.right
import com.agalobr.superheroe.app.extensions.JsonSerialization
import com.agalobr.superheroe.features.domain.Powerstats

class XmlPowerstatsLocalDataSource(context: Context, private val serialization: JsonSerialization) {

    private val sharedPref = context.getSharedPreferences("Powerstats", Context.MODE_PRIVATE)

    fun savePowerstatsById(heroId: Int, powerstats: Powerstats): Either<ErrorApp, Powerstats> {
        return try {
            val editor = sharedPref.edit()
            editor.putString(
                heroId.toString(),
                serialization.toJson(powerstats, Powerstats::class.java)
            ).apply()
            return powerstats.right()
        } catch (ex: Exception) {
            ErrorApp.DatabaseErrorApp.left()
        }
    }

    fun getPowerstatsById(heroId: Int): Either<ErrorApp, Powerstats?> {
        return try {
            sharedPref.getString(heroId.toString(), null).let {
                serialization.fromJson(it!!, Powerstats::class.java)
            }.right()
        } catch (ex: Exception) {
            ErrorApp.DatabaseErrorApp.left()
        }
    }
}