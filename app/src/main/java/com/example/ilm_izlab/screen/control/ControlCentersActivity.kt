package com.example.ilm_izlab.screen.control

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ilm_izlab.databinding.ActivityControlCentersBinding
import com.example.ilm_izlab.screen.control.addCenters.AddCenterActivity
import com.example.ilm_izlab.screen.main.MainActivity

class ControlCentersActivity : AppCompatActivity() {

    lateinit var binding: ActivityControlCentersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlCentersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddCenterActivity::class.java))
        }
    }
}