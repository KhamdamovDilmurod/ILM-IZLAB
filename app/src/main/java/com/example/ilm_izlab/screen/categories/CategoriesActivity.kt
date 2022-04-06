package com.example.ilm_izlab.screen.categories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ilm_izlab.adapters.*
import com.example.ilm_izlab.databinding.ActivityCategoriesBinding
import com.example.ilm_izlab.model.CategoryModel
import com.example.ilm_izlab.model.DistrictsModel
import com.example.ilm_izlab.model.LCFilterModel
import com.example.ilm_izlab.model.SciencesModel
import com.example.ilm_izlab.screen.main.MainViewModel
import com.example.ilm_izlab.screen.select.area.SelectAreaActivity
import com.example.ilm_izlab.utils.Constants
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class CategoriesActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoriesBinding

    lateinit var viewModel: MainViewModel

    lateinit var item: CategoryModel

    lateinit var filter: LCFilterModel

    var selectedDistrict: DistrictsModel? = null

    var districtResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.data != null && result.resultCode == RESULT_OK && result.data!!.hasExtra(
                    Constants.EXTRA_DATA
                )
            ) {
                selectedDistrict =
                    result.data!!.getSerializableExtra(Constants.EXTRA_DATA) as DistrictsModel
                filter.district_id = selectedDistrict?.id ?: 0
                binding.btnRegion.text = selectedDistrict?.name_uz
                loadData()
            }
        }

    // -> for alert dialog
    var selectedItemIndex = 1
    private var arrItems = arrayOf("Eng yaqinlari", "Eng saralari")
    var selectedItem = arrItems[selectedItemIndex]

    override fun onCreate(savedInstanceState: Bundle?) {
        binding  = ActivityCategoriesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

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

        item = intent.getSerializableExtra(Constants.EXTRA_DATA) as CategoryModel

        filter = LCFilterModel(
            0,0,item.id,0,"","",
            40,40.3680081,71.7810391,false
        )

        viewModel.getTopCenters(filter)

        binding.title.text = item.title

        binding.recylerCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.recylerCategories.adapter = CategoryDetailAdapter(item.sciences,object: CategoryDetailAdapterCallback{
            override fun onClickItem(item: SciencesModel) {
                filter.science_id = item.id
                loadData()
            }
        })

        viewModel.topTrainingCentresData.observe(this, Observer {
            binding.recyclerSearch.layoutManager = GridLayoutManager(this, 2)
            binding.recyclerSearch.adapter = NearTrainingCentersAdapter(it)
        })

        binding.btnRegion.setOnClickListener {
            val intent = Intent(this, SelectAreaActivity::class.java)
            if (selectedDistrict != null) {
                intent.putExtra(Constants.EXTRA_DATA, selectedDistrict as Serializable)
            }
            districtResultLauncher.launch(intent)
        }

        binding.dialogButton.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Saralash")
                .setSingleChoiceItems(arrItems, selectedItemIndex) { dialog, which ->
                    selectedItemIndex = which
                    selectedItem = arrItems[which]
                    binding.dialogButton.text = selectedItem
                    if (selectedItem == "Eng yaqinlari") {
                        filter.sort = "distance"
                        loadData()
                    } else {
                        filter.sort = "rating"
                        loadData()
                    }
                    showSnackBar(it, "$selectedItem Selected")
                    dialog.dismiss()
                }.show()
        }

    }

    private fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }

    fun loadData(){
        viewModel.getTopCenters(filter)
    }
}