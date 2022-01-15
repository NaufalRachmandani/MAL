package com.naufal.core.data.source.remote.model.anime_characters


import com.google.gson.annotations.SerializedName
import com.naufal.core.domain.model.anime_characters.Images

data class ImagesDto(
    @SerializedName("jpg")
    val jpgDto: JpgDto? = JpgDto()
)

fun ImagesDto.toImages(): Images {
    return Images(jpgDto?.toJpg())
}