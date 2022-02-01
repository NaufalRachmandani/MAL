package com.naufal.core.domain.model.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Jpg(
    @SerializedName("image_url")
    val imageUrl: String? = "",
    @SerializedName("large_image_url")
    val largeImageUrl: String? = "",
    @SerializedName("small_image_url")
    val smallImageUrl: String? = ""
): Parcelable
