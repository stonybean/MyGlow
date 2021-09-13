package com.github.stonybean.myglow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.stonybean.myglow.model.Products
import com.github.stonybean.myglow.model.Recommends
import com.github.stonybean.myglow.repository.GlowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Joo on 2021/09/10
 */

@HiltViewModel
class GlowViewModel @Inject constructor(private val repository: GlowRepository) :
    ViewModel() {

    private val _productsData = MutableLiveData<Products>()
    val productData = _productsData

    private val _recommendData = MutableLiveData<Recommends>()
    val recommendData = _recommendData

    // 제품 목록
    fun getProducts(number: Int) {
        viewModelScope.launch {
            repository.getProducts(number)?.let { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        _productsData.postValue(it)
                    }
                } else {
                    throw Exception()
                }
            }
        }
    }

    // 제품 추천 목록
    fun getRecommend() {
        viewModelScope.launch {
            repository.getRecommends()?.let { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        _recommendData.postValue(it)
                    }
                } else {
                    throw Exception()
                }
            }
        }
    }
}