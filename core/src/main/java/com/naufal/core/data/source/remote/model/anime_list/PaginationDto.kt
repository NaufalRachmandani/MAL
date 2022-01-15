package com.naufal.core.data.source.remote.model.anime_list

import com.google.gson.annotations.SerializedName

data class PaginationDto(
    @SerializedName("last_visible_page")
    val lastVisiblePage: Int? = 0,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean? = false,
)