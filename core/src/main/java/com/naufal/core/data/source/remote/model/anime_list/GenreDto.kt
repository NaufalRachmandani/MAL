package com.naufal.core.data.source.remote.model.anime_list

import com.google.gson.annotations.SerializedName
import com.naufal.core.domain.model.anime.Genre

data class GenreDto(
    @SerializedName("mal_id")
    val malId: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = ""
)

fun GenreDto.toGenre(): Genre {
    return Genre(
        malId = malId,
        name = name,
        type = type,
        url = url
    )
}