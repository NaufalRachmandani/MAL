package com.naufal.core.domain.model.anime

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images(
    val jpg: Jpg? = Jpg()
): Parcelable
