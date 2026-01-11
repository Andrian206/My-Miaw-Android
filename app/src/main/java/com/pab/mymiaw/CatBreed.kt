package com.pab.mymiaw

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatBreed(
    @SerializedName("name") val name: String,
    @SerializedName("origin") val origin: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("life_span") val life_span: String?,
    @SerializedName("temperament") val temperament: String?,
    @SerializedName("image") val image: CatImage?,
    @SerializedName("reference_image_id") val referenceImageId: String?
) : Parcelable

@Parcelize
data class CatImage(
    @SerializedName("url") val url: String?
) : Parcelable