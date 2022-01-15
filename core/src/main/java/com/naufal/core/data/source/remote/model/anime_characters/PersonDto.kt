package com.naufal.core.data.source.remote.model.anime_characters


import com.google.gson.annotations.SerializedName
import com.naufal.core.domain.model.anime_characters.Person

data class PersonDto(
    @SerializedName("images")
    val imagesDto: ImagesDto? = ImagesDto(),
    @SerializedName("name")
    val name: String? = "",
)

fun PersonDto.toPerson(): Person {
    return Person(images = imagesDto?.toImages(), name = name)
}