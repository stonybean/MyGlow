package com.github.stonybean.myglow.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Joo on 2021/09/10
 */
data class Products(
    @SerializedName("products")
    val products: List<Product>
)

data class Product(
    @SerializedName("idProduct")
    val idProduct: Int,

    @SerializedName("idBrand")
    val idBrand: Int,

    @SerializedName("productTitle")
    val productTitle: String,

    @SerializedName("volume")
    val volume: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("productScore")
    val productScore: Double,

    @SerializedName("ratingAvg")
    val ratingAvg: Double,

    @SerializedName("productRank")
    val productRank: String,

    @SerializedName("rankChange")
    val rankChange: String,

    @SerializedName("rankChangeType")
    val rankChangeType: String,

    @SerializedName("reviewCount")
    val reviewCount: String,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("brand")
    val brand: Brand,
): Serializable

data class Brand(
    @SerializedName("idBrand")
    val idBrand: Int,

    @SerializedName("brandTitle")
    val brandTitle: String,

    @SerializedName("imageUrl")
    val imageUrl: String
)