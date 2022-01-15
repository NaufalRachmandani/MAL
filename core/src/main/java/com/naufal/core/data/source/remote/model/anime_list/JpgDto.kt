package com.naufal.core.data.source.remote.model.anime_list


import com.google.gson.annotations.SerializedName
import com.naufal.core.domain.model.anime.Jpg

data class JpgDto(
    @SerializedName("image_url")
    val imageUrl: String? = "",
    @SerializedName("large_image_url")
    val largeImageUrl: String? = "",
    @SerializedName("small_image_url")
    val smallImageUrl: String? = ""
)

fun JpgDto.toJpg(): Jpg {
    return Jpg(
        imageUrl = imageUrl,
        largeImageUrl = largeImageUrl,
        smallImageUrl = smallImageUrl
    )
}