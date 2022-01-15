package com.naufal.core.data.source.local.model.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.naufal.core.domain.model.anime.Genre

@Entity
data class GenreEntity(
    @PrimaryKey
    val malId: Int? = 0,
    val name: String? = "",
    val type: String? = "",
    val url: String? = ""
)

fun GenreEntity.toGenre(): Genre {
    return Genre(
        malId = malId,
        name = name,
        type = type,
        url = url
    )
}

fun Genre.toGenreEntity(): GenreEntity {
    return GenreEntity(
        malId = malId,
        name = name,
        type = type,
        url = url
    )
}