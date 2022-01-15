package com.naufal.core.data.source.local.model.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.naufal.core.domain.model.anime.Jpg

@Entity
data class JpgEntity(
    @PrimaryKey
    val imageUrl: String? = "",
    val largeImageUrl: String? = "",
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