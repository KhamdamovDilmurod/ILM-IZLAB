package com.example.ilm_izlab.screen.teacher

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ilm_izlab.R
import com.example.ilm_izlab.adapters.TeachersAdapter
import com.example.ilm_izlab.databinding.ActivityTeacherBinding
import com.example.ilm_izlab.model.TrainingCentersModel
import com.example.ilm_izlab.screen.main.MainViewModel
import com.example.ilm_izlab.utils.Constants

class TeacherActivity : AppCompatActivity() {

    lateinit var binding: ActivityTeacherBinding

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTeacherBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.progress.observe(this, Observer {
            binding.swipe.isRefreshing = it
        })

        loadData()

        viewModel.courseTeachersData.observe(this, Observer {
            binding.recyclerTeachers.layoutManager = LinearLayoutManager(this)
            binding.recyclerTeachers.adapter = TeachersAdapter(it)
        })
    }

    fun loadData(){
        val id = intent.getIntExtra("course_id",30)
        viewModel.getCourseTeachers(id)
    }
}