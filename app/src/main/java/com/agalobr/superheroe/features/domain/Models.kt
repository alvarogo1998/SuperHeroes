package com.agalobr.superheroe.features.domain

data class SuperHeroe(
    val id: Int, val name: String, val urlImages: List<String>
){
    fun getUrlImageS(): String = urlImages[0]
    fun getUrlImageM(): String = urlImages[1]
    fun getUrlImageL(): String = urlImages[2]
    fun getUrlImageXL(): String = urlImages[3]
}

data class Biography(
    val fullName: String
)

data class Work(
    val occupation: String
)