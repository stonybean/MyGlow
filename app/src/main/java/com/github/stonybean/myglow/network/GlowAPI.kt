package com.github.stonybean.myglow.network

import com.github.stonybean.myglow.model.Products
import com.github.stonybean.myglow.model.Recommends
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Joo on 2021/09/10
 */
interface GlowAPI {
    // 상품 리스트 요청
    @GET("product.{number}.json")
    suspend fun getProducts(
        @Path("number") number: Int
    ): Response<Products>

    // 추천 상품 리스트 요청
    @GET("recommend_product.json")
    suspend fun getRecommends(): Response<Recommends>
}