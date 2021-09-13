package com.github.stonybean.myglow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.stonybean.myglow.databinding.ActivityMainBinding

/**
 * Created by Joo on 2021/09/10
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}