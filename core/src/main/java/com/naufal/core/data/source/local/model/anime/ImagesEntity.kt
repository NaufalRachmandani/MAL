package com.naufal.core.data.source.local.model.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.naufal.core.domain.model.anime.Images

@Entity
data class ImagesEntity(
    @PrimaryKey
    val jpg: JpgEntity? = JpgEntity()
)

fun ImagesEntity.toImages(): Images {
    return Images(
        jpg = jpg?.toJpg()
    )
}

fun Images.toImagesEntity(): ImagesEntity {
    return ImagesEntity(
        jpg = jpg?.toJpgEntity()
    )
}
