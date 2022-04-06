package com.example.ilm_izlab.screen.offers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ilm_izlab.R
import com.example.ilm_izlab.databinding.ActivityOffersBinding
import com.example.ilm_izlab.utils.Constants

class OffersActivity : AppCompatActivity() {

    lateinit var binding: ActivityOffersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOffersBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val title = intent.getStringExtra(Constants.EXTRA_DATA)

        binding.title.text = title
        binding.tvArticle.text = title

        binding.imgBack.setOnClickListener {
            finish()
        }

    }
}