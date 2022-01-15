package com.naufal.core.data.source.remote.model.anime_characters

import com.google.gson.annotations.SerializedName

data class AnimeCharactersResponse(
    @SerializedName("data")
    val data: List<CharacterDataDto>? = listOf()
)