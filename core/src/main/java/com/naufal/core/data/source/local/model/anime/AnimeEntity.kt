package com.naufal.core.data.source.local.model.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.naufal.core.domain.model.anime.Anime

@Entity
data class AnimeEntity(
    @SerializedName("episodes")
    val episodes: Int? = 0,
    @SerializedName("genres")
    val genres: List<GenreEntity>? = listOf(),
    @SerializedName("images")
    val images: ImagesEntity? = ImagesEntity(),
    @SerializedName("mal_id")
    @PrimaryKey
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

fun AnimeEntity.toAnime(): Anime {
    return Anime(
        episodes = episodes,
        genres = genres?.map { it.toGenre() },
        images = images?.toImages(),
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

fun Anime.toAnimeEntity(): AnimeEntity {
    return AnimeEntity(
        episodes = episodes,
        genres = genres?.map { it.toGenreEntity() },
        images = images?.toImagesEntity(),
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