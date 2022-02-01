package com.naufal.core.domain.model.anime_characters

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("jpg")
    val jpg: Jpg? = Jpg()
)