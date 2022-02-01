package com.naufal.core.domain.model.anime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images(
    @SerializedName("jpg")
    val jpg: Jpg? = Jpg()
): Parcelable
