package com.naufal.core.domain.model.anime_characters

import com.google.gson.annotations.SerializedName

data class Jpg(
    @SerializedName("image_url")
    val imageUrl: String? = ""
)