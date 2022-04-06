package com.example.ilm_izlab.screen.select.sciences

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ilm_izlab.adapters.SelectCategoryAdapter
import com.example.ilm_izlab.databinding.ActivitySelectSciencesBinding
import com.example.ilm_izlab.model.SciencesModel
import com.example.ilm_izlab.screen.main.MainViewModel
import com.example.ilm_izlab.utils.Constants

class SelectSciencesActivity : AppCompatActivity() {

    lateinit var binding: ActivitySelectSciencesBinding

    lateinit var viewModel: MainViewModel

    var selectedScience: SciencesModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySelectSciencesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = MainViewModel()

        if (intent.hasExtra(Constants.EXTRA_DATA)) {
            selectedScience = intent.getSerializableExtra(Constants.EXTRA_DATA) as SciencesModel
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.categoriesData.observe(this, Observer {
            it.forEach {
                it.sciences.forEach {
                    if (it.id == selectedScience?.id) {
                        it.checked = true
                    }
                }
            }
            binding.recyclerScience.layoutManager = LinearLayoutManager(this)
            binding.recyclerScience.adapter = SelectCategoryAdapter(it)
        })

        binding.btnSelect.setOnClickListener {
            viewModel.categoriesData.value?.forEach {
                it.sciences.forEach {
                    if (it.checked) {
                        selectedScience = it
                    }
                }
            }

            if(selectedScience == null){
                Toast.makeText(this, "Iltimos yo'nalish tanlang", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent()
                intent.putExtra(Constants.EXTRA_DATA, selectedScience)
                setResult(RESULT_OK, intent)
                finish()
            }

        }
        loadData()
    }

    fun loadData() {
        viewModel.getCategories()
    }
}