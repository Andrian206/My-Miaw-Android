package com.pab.mymiaw.data.model

import java.io.Serializable

data class Cat(
    val id: String,
    val name: String,
    val description: String,
    val origin: String,
    val life_span: String,
    val temperament: String,
    val image: CatImage?
) : Serializable

data class CatImage(
    val id: String?,
    val url: String?
) : Serializable
