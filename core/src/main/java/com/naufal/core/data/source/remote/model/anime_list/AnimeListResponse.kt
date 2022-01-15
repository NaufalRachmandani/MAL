package com.naufal.core.data.source.remote.model.anime_list

import com.google.gson.annotations.SerializedName

data class AnimeListResponse(
    @SerializedName("pagination")
    val paginationDto: PaginationDto? = PaginationDto(),
    @SerializedName("data")
    val data: List<AnimeDto>? = listOf()
)