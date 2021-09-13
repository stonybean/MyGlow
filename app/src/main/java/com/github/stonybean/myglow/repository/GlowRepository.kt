package com.github.stonybean.myglow.repository

import com.github.stonybean.myglow.network.BaseService
import com.github.stonybean.myglow.network.GlowAPI

/**
 * Created by Joo on 2021/09/10
 */
class GlowRepository {
    private val glowApi = BaseService().getClient()?.create(GlowAPI::class.java)

    suspend fun getProducts(number: Int) = glowApi?.getProducts(number)

    suspend fun getRecommends() = glowApi?.getRecommends()
}