package com.agalobr.superheroe.features.data.connections.local

import android.content.Context
import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.app.error.right
import com.agalobr.superheroe.app.extensions.JsonSerialization
import com.agalobr.superheroe.features.domain.Connections

class XmlConnectionsLocalDataSource(
    context: Context,
    private val serialization: JsonSerialization
) {

    private val sharedPref = context.getSharedPreferences("Connections", Context.MODE_PRIVATE)

    fun saveConnectionsById(heroId: Int, connections: Connections): Either<ErrorApp, Connections> {
        return try {
            val editor = sharedPref.edit()
            editor.putString(
                heroId.toString(),
                serialization.toJson(connections, Connections::class.java)
            ).apply()
            return connections.right()
        } catch (ex: Exception) {
            ErrorApp.DatabaseErrorApp.left()
        }
    }

    fun getConnectionsById(id: Int): Either<ErrorApp, Connections?> {
        return try {
            sharedPref.getString(id.toString(), null).let {
                serialization.fromJson(it!!, Connections::class.java)
            }.right()
        } catch (ex: Exception) {
            ErrorApp.DatabaseErrorApp.left()
        }
    }

    fun delete(): Either<ErrorApp, Boolean> {
        sharedPref.edit().clear().apply()
        return true.right()
    }
}