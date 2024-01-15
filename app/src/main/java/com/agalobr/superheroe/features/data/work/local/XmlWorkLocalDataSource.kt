package com.agalobr.superheroe.features.data.work.local

import android.content.Context
import com.agalobr.superheroe.app.error.Either
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.error.left
import com.agalobr.superheroe.app.error.right
import com.agalobr.superheroe.app.extensions.JsonSerialization
import com.agalobr.superheroe.features.domain.Work

class XmlWorkLocalDataSource(context: Context, private val serialization: JsonSerialization) {

    private val sharedPref = context.getSharedPreferences("Work", Context.MODE_PRIVATE)

    fun saveWorkById(heroId: Int, work: Work): Either<ErrorApp, Work> {
        return try {
            val editor = sharedPref.edit()
            editor.putString(
                heroId.toString(),
                serialization.toJson(work, Work::class.java)
            ).apply()
            return work.right()
        } catch (ex: Exception) {
            ErrorApp.DatabaseErrorApp.left()
        }
    }

    fun getWorkById(id: Int): Either<ErrorApp, Work?> {
        return try {
            sharedPref.getString(id.toString(), null).let {
                serialization.fromJson(it!!, Work::class.java)
            }.right()
        }catch (ex: Exception){
            ErrorApp.DatabaseErrorApp.left()
        }
    }
}