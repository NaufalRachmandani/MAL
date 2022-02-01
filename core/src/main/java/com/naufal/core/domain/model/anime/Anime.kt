package com.naufal.core.domain.model.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Anime(
    @SerializedName("episodes")
    val episodes: Int? = 0,
    @SerializedName("genres")
    val genres: List<Genre>? = listOf(),
    @SerializedName("images")
    val images: Images? = Images(),
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
): Parcelable