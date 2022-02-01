package com.naufal.core.data.source.local.model.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.naufal.core.domain.model.anime.Genre

@Entity
data class GenreEntity(
    @SerializedName("mal_id")
    @PrimaryKey
    val malId: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("url")
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