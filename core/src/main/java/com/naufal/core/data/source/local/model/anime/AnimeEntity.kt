package com.naufal.core.data.source.local.model.anime

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.naufal.core.domain.model.anime.Anime

@Entity
data class AnimeEntity(
    val episodes: Int? = 0,
    val genres: List<GenreEntity>? = listOf(),
    val images: ImagesEntity? = ImagesEntity(),
    @PrimaryKey
    val malId: Int? = 0,
    val popularity: Int? = 0,
    val rank: Int? = 0,
    val rating: String? = "",
    val score: Double? = 0.0,
    val season: String? = "",
    val source: String? = "",
    val status: String? = "",
    val synopsis: String? = "",
    val title: String? = "",
    val type: String? = "",
    val url: String? = "",
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