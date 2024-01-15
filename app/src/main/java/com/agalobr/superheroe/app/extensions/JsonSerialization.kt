package com.agalobr.superheroe.app.extensions

interface JsonSerialization {

    fun <T> toJson(obj: T, typeClass: Class<T>): String

    fun <T> toJsonOut(obj: T, typeClass: Class<out T>): String

    fun <T> fromJson(json: String, typeClass: Class<T>): T

}