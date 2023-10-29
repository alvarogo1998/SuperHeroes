package com.agalobr.superheroe.features.domain

data class SuperHeroe(
    val id: Int, val name: String, val urlImages: List<String>
) {
    fun getUrlImageS(): String = urlImages[0]
    fun getUrlImageM(): String = urlImages[1]
    fun getUrlImageL(): String = urlImages[2]
    fun getUrlImageXL(): String = urlImages[3]
}

data class Biography(
    val fullName: String,
    val alignment: String
)

data class Work(
    val occupation: String
)

data class Connections(
    val groupAffiliation: String
)

data class Powerstats(
    val intelligence: Int,
    val strength: Int,
    val speed: Int,
    val durability: Int,
    val power: Int,
    val combat: Int,
)