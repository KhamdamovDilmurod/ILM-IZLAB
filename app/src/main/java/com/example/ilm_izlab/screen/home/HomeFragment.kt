package com.example.ilm_izlab.screen.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.ilm_izlab.adapters.CategoryAdapter
import com.example.ilm_izlab.adapters.NearTrainingCentersAdapter
import com.example.ilm_izlab.adapters.TrainingCentersAdapter
import com.example.ilm_izlab.databinding.FragmentHomeBinding
import com.example.ilm_izlab.model.LCFilterModel
import com.example.ilm_izlab.screen.main.MainViewModel
import com.example.ilm_izlab.screen.offers.OffersActivity
import com.example.ilm_izlab.utils.Constants


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private lateinit var filter: LCFilterModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        viewModel.offersData.observe(viewLifecycleOwner, Observer {
            imageList.clear()
            it.forEach {
                imageList.add(SlideModel(Constants.HOST_IMAGE + it.image, ScaleTypes.CENTER_CROP))
            }
            binding.imageSlider.setImageList(imageList)

            binding.imageSlider.setItemClickListener(object : ItemClickListener {
                override fun onItemSelected(position: Int) {
                    val intent = Intent(requireContext(),OffersActivity::class.java)
                    intent.putExtra(Constants.EXTRA_DATA, it[position].title)
                    startActivity(intent)
                }
            })
        })

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipe.isRefreshing = false
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.categoriesData.observe(viewLifecycleOwner, Observer {

            binding.recylerCategories.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            binding.recylerCategories.adapter = CategoryAdapter(it)

        })

        viewModel.topTrainingCentresData.observe(viewLifecycleOwner, Observer {

            binding.recyclerTop.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerTop.adapter = TrainingCentersAdapter(it)


        })

        viewModel.nearTrainingCentresData.observe(viewLifecycleOwner, Observer {

            binding.recyclerNear.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recyclerNear.adapter = NearTrainingCentersAdapter(it)
        })
        loadData()
    }

    fun loadData() {
        viewModel.getOffers()
        viewModel.getCategories()

        filter = LCFilterModel(
            0, 0, 0, 0, "", "rating",
            40, 0.0, 0.0, false
        )
        viewModel.getTopCenters(filter)
        viewModel.getNearCenters()

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}