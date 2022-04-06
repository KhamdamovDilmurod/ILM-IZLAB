package com.example.ilm_izlab.screen.news.newsdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import com.bumptech.glide.Glide
import com.example.ilm_izlab.R
import com.example.ilm_izlab.databinding.ActivityMainBinding
import com.example.ilm_izlab.databinding.ActivityNewsDetailBinding
import com.example.ilm_izlab.model.AllNewsModel
import com.example.ilm_izlab.utils.Constants

class NewsDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewsDetailBinding

    lateinit var item:AllNewsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            finish()
        }

        item = intent.getSerializableExtra(Constants.EXTRA_DATA) as AllNewsModel

        binding.title.text = item.title
        binding.description.text = item.content
        Glide.with(this).load(Constants.HOST_IMAGE + item.image).into(binding.image)

    }
}