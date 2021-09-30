package com.example.awesomeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.awesomeapp.R
import com.example.awesomeapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val PHOTO = "photo"
        const val NAME = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val img = intent.getStringExtra(PHOTO)
        val name = intent.getStringExtra(NAME)

        binding.imgDetail.load(img) {
            crossfade(true)
            crossfade(1000)
            placeholder(android.R.color.darker_gray)
            error(R.drawable.nodata)
        }
        binding.tvDetailName.text = getString(R.string.name, name)
    }
}