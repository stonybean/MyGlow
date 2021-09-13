package com.github.stonybean.myglow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.stonybean.myglow.repository.GlowRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Joo on 2021/09/10
 */
//@Singleton
//class GlowViewModelFactory @Inject constructor(private val repository: GlowRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return modelClass.getConstructor(GlowRepository::class.java).newInstance(repository)
//    }
//}