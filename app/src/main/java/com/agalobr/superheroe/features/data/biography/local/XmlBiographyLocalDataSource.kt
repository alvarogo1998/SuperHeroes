package com.agalobr.superheroe.features.data.biography.local

import android.content.Context
import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.app.error.right
import com.agalobr.superheroe.app.extensions.JsonSerialization
import com.agalobr.superheroe.features.domain.Biography

class XmlBiographyLocalDataSource(context: Context, private val serialization: JsonSerialization) {

    private val sharedPref = context.getSharedPreferences("Biography", Context.MODE_PRIVATE)

    fun saveBiographyById(heroId: Int, biography: Biography): Either<ErrorApp, Biography> {
        return try {
            val editor = sharedPref.edit()
            editor.putString(
                heroId.toString(),
                serialization.toJson(biography, Biography::class.java)
            ).apply()
            return biography.right()
        } catch (ex: Exception) {
            ErrorApp.DatabaseErrorApp.left()
        }
    }

    fun getBiographyById(id: Int): Either<ErrorApp, Biography?> {
        return try {
            sharedPref.getString(id.toString(), null).let {
                serialization.fromJson(it!!, Biography::class.java)
            }.right()
        } catch (ex: Exception) {
            ErrorApp.DatabaseErrorApp.left()
        }
    }
}