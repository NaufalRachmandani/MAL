package com.naufal.core.domain.model.anime

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Anime(
    val episodes: Int? = 0,
    val genres: List<Genre>? = listOf(),
    val images: Images? = Images(),
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
): Parcelable