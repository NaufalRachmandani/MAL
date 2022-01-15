package com.naufal.core.data.source.remote.model.anime_characters


import com.google.gson.annotations.SerializedName
import com.naufal.core.domain.model.anime_characters.Character

data class CharacterDto(
    @SerializedName("images")
    val imagesDto: ImagesDto? = ImagesDto(),
    @SerializedName("name")
    val name: String? = "",
)

fun CharacterDto.toCharacter(): Character {
    return Character(images = imagesDto?.toImages(), name = name)
}