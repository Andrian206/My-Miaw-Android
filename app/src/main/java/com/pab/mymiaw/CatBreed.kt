package com.pab.mymiaw

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatBreed(
    val name: String,
    val origin: String?,
    val description: String?,
    val life_span: String?,
    val temperament: String?,
    val image: CatImage?
) : Parcelable

@Parcelize
data class CatImage(val url: String?) : Parcelable