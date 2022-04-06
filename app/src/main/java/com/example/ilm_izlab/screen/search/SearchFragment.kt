package com.example.ilm_izlab.screen.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ilm_izlab.adapters.NearTrainingCentersAdapter
import com.example.ilm_izlab.adapters.TrainingCentersAdapter
import com.example.ilm_izlab.databinding.FragmentSearchBinding
import com.example.ilm_izlab.model.DistrictsModel
import com.example.ilm_izlab.model.LCFilterModel
import com.example.ilm_izlab.model.SciencesModel
import com.example.ilm_izlab.screen.main.MainViewModel
import com.example.ilm_izlab.screen.select.area.SelectAreaActivity
import com.example.ilm_izlab.screen.select.sciences.SelectSciencesActivity
import com.example.ilm_izlab.utils.Constants
import com.example.ilm_izlab.utils.PrefUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable


class SearchFragment : Fragment(), SearchView.OnQueryTextListener {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private lateinit var filter: LCFilterModel

    // -> for alert dialog
    var selectedItemIndex = 1
    private var arrItems = arrayOf("Eng yaqinlari", "Eng saralari")
    var selectedItem = arrItems[selectedItemIndex]

    // result activity
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
    var selectedScience: SciencesModel? = null

    var sciencesResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.data != null && result.resultCode == RESULT_OK && result.data!!.hasExtra(
                    Constants.EXTRA_DATA
                )
            ) {
                selectedScience =
                    result.data!!.getSerializableExtra(Constants.EXTRA_DATA) as SciencesModel
                filter.science_id = selectedScience?.id ?: 0
                binding.btnScience.text = selectedScience?.title
                loadData()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        filter = LCFilterModel(
            0, 0, 0, 0, "", "rating",
            40, PrefUtils.getLocation()?.latitude?:0.0, PrefUtils.getLocation()?.longitude?:0.0, false
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            binding.swipe.isRefreshing = false
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.nearTrainingCentresData.observe(viewLifecycleOwner, Observer {
            binding.recyclerSearch.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recyclerSearch.adapter = NearTrainingCentersAdapter(it)
        })

        binding.btnRegion.setOnClickListener {
            val intent = Intent(requireActivity(), SelectAreaActivity::class.java)
            if (selectedDistrict != null) {
                intent.putExtra(Constants.EXTRA_DATA, selectedDistrict as Serializable)
            }
            districtResultLauncher.launch(intent)
        }

        binding.btnScience.setOnClickListener {
            val intent = Intent(requireActivity(), SelectSciencesActivity::class.java)
            if (selectedScience != null) {
                intent.putExtra(Constants.EXTRA_DATA, selectedScience as Serializable)
            }
            sciencesResultLauncher.launch(intent)
        }

        binding.dialogButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
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

        binding.searchView.setOnQueryTextListener(this)
        loadData()
    }

    private fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }

    fun loadData() {
        viewModel.getNearCenters(filter)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val items = viewModel.nearTrainingCentresData.value?.filter {
            it.name.uppercase().contains(newText!!.uppercase())
        }
        binding.recyclerSearch.adapter = NearTrainingCentersAdapter(items ?: emptyList())
        return false
    }
}