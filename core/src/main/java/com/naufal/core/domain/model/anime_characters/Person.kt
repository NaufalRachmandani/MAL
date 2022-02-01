package com.naufal.core.domain.model.anime_characters

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("images")
    val images: Images? = Images(),
    @SerializedName("name")
    val name: String? = "",
)