package com.example.ilm_izlab.screen.ilovahaqida

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ilm_izlab.databinding.ActivityAboutAppBinding
import com.example.ilm_izlab.screen.main.MainActivity

class AboutApp : AppCompatActivity() {

    lateinit var binding: ActivityAboutAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}