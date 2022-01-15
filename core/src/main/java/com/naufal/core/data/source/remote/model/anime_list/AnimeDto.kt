package com.naufal.core.data.source.remote.model.anime_list

import com.google.gson.annotations.SerializedName
import com.naufal.core.domain.model.anime.Anime

data class AnimeDto(
    @SerializedName("episodes")
    val episodes: Int? = 0,
    @SerializedName("genres")
    val genresDto: List<GenreDto>? = listOf(),
    @SerializedName("images")
    val imagesDto: ImagesDto? = ImagesDto(),
    @SerializedName("mal_id")
    val malId: Int? = 0,
    @SerializedName("popularity")
    val popularity: Int? = 0,
    @SerializedName("rank")
    val rank: Int? = 0,
    @SerializedName("rating")
    val rating: String? = "",
    @SerializedName("score")
    val score: Double? = 0.0,
    @SerializedName("season")
    val season: String? = "",
    @SerializedName("source")
    val source: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("synopsis")
    val synopsis: String? = "",
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("year")
    val year: Int? = 0
)

fun AnimeDto.toAnime(): Anime {
    return Anime(
        episodes = episodes,
        genres = genresDto?.map { it.toGenre() },
        images = imagesDto?.toImages(),
        malId = malId,
        popularity = popularity,
        rank = rank,
        rating = rating,
        score = score,
        season = season,
        source = source,
        status = status,
        synopsis = synopsis,
        title = title,
        type = type,
        url = url,
        year = year
    )
}