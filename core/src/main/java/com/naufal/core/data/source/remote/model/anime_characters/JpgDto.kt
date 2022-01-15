package com.naufal.core.data.source.remote.model.anime_characters


import com.google.gson.annotations.SerializedName
import com.naufal.core.domain.model.anime_characters.Jpg

data class JpgDto(
    @SerializedName("image_url")
    val imageUrl: String? = ""
)

fun JpgDto.toJpg(): Jpg {
    return Jpg(imageUrl)
}