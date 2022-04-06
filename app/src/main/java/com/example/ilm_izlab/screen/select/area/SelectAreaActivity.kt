package com.example.ilm_izlab.screen.select.area

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ilm_izlab.adapters.SelectRegionsAdapter
import com.example.ilm_izlab.databinding.ActivitySelectAreaBinding
import com.example.ilm_izlab.model.DistrictsModel
import com.example.ilm_izlab.screen.main.MainViewModel
import com.example.ilm_izlab.utils.Constants


class SelectAreaActivity : AppCompatActivity() {

    lateinit var binding: ActivitySelectAreaBinding

    lateinit var viewModel: MainViewModel

    var selectedDistrict: DistrictsModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySelectAreaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = MainViewModel()

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }


        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        //----------------------->>>>>>>

        if (intent.hasExtra(Constants.EXTRA_DATA)) {
            selectedDistrict = intent.getSerializableExtra(Constants.EXTRA_DATA) as DistrictsModel
        }

        viewModel.regionsData.observe(this, Observer {
            it.forEach {
                it.districts.forEach {
                    if (it.id == selectedDistrict?.id) {
                        it.checked = true
                    }
                }
            }
            binding.recyclerRegions.layoutManager = LinearLayoutManager(this)
            binding.recyclerRegions.adapter = SelectRegionsAdapter(it)
        })

        binding.btnSelect.setOnClickListener {
            viewModel.regionsData.value?.forEach {
                it.districts.forEach {
                    if (it.checked) {
                        selectedDistrict = it
                    }
                }
            }
            if (selectedDistrict == null){
                Toast.makeText(this, "Iltimos hududni tanlang", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent()
                intent.putExtra(Constants.EXTRA_DATA, selectedDistrict)
                setResult(RESULT_OK, intent)
                finish()
            }

        }
        loadData()
    }

    fun loadData() {
        viewModel.getRegions()
    }
}