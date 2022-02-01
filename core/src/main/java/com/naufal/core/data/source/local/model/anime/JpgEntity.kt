package com.naufal.core.data.source.local.model.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.naufal.core.domain.model.anime.Jpg

@Entity
data class JpgEntity(
    @SerializedName("image_url")
    @PrimaryKey
    val imageUrl: String? = "",
    @SerializedName("large_imageUrl")
    val largeImageUrl: String? = "",
    @SerializedName("small_imageUrl")
    val smallImageUrl: String? = ""
)

fun JpgEntity.toJpg(): Jpg {
    return Jpg(
        imageUrl = imageUrl,
        largeImageUrl = largeImageUrl,
        smallImageUrl = smallImageUrl
    )
}

fun Jpg.toJpgEntity(): JpgEntity {
    return JpgEntity(
        imageUrl = imageUrl,
        largeImageUrl = largeImageUrl,
        smallImageUrl = smallImageUrl
    )
}