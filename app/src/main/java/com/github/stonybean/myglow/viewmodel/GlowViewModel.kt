package com.github.stonybean.myglow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.stonybean.myglow.model.Product
import com.github.stonybean.myglow.model.Products
import com.github.stonybean.myglow.model.Recommend
import com.github.stonybean.myglow.model.Recommends
import com.github.stonybean.myglow.repository.GlowRepository
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by Joo on 2021/09/10
 */
class GlowViewModel(private val repository: GlowRepository): ViewModel() {

    private val _productsData = MutableLiveData<Products>()
    val productData = _productsData

    private val _recommendData = MutableLiveData<Recommends>()
    val recommendData = _recommendData

    fun getProducts(number: String) {
        viewModelScope.launch {
            repository.getProducts(number)?.let { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        println("Products : $it")
                        _productsData.postValue(it)
                    }
                } else {
                    throw Exception()
                }
            }
        }
    }

    fun getRecommend() {
        viewModelScope.launch {
            repository.getRecommends()?.let { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        println("Recommends : $it")
                        _recommendData.postValue(it)
                    }
                } else {
                    throw Exception()
                }
            }
        }
    }
}