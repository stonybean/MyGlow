package com.github.stonybean.myglow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.stonybean.myglow.repository.GlowRepository

/**
 * Created by Joo on 2021/09/10
 */
class GlowViewModelFactory(private val repository: GlowRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GlowRepository::class.java).newInstance(repository)
    }
}