package com.github.stonybean.myglow.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Recommends(
    @SerializedName("recommend1")
    val recommend1: List<Recommend>,

    @SerializedName("recommend2")
    val recommend2: List<Recommend>,

    @SerializedName("recommend3")
    val recommend3: List<Recommend>
)

data class Recommend(
    @SerializedName("idProduct")
    val idProduct: Int,

    @SerializedName("productTitle")
    val productTitle: String,

    @SerializedName("ratingAvg")
    val ratingAvg: Double,

    @SerializedName("reviewCount")
    val reviewCount: String,

    @SerializedName("imageUrl")
    val imageUrl: String
): Serializable