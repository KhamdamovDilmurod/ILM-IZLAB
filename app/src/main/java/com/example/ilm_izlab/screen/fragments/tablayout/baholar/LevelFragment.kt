package com.example.ilm_izlab.screen.fragments.tablayout.baholar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ilm_izlab.adapters.BaholarAdapter
import com.example.ilm_izlab.databinding.FragmentLevelBinding
import com.example.ilm_izlab.screen.main.MainViewModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LevelFragment(var centerId:Int) : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentLevelBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        super.onCreate(savedInstanceState)
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
        _binding = FragmentLevelBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipe.isRefreshing = it
        })
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.getRatingsData.observe(requireActivity(), Observer {
            binding.recyclerBaholar.layoutManager =
                LinearLayoutManager(requireActivity())
            binding.recyclerBaholar.adapter = BaholarAdapter(it)
        })
        loadData()
    }
    fun loadData() {
        viewModel.getRatings(centerId)
    }

//    companion object {
//
//        @JvmStatic
//        fun newInstance() = LevelFragment()
//    }
}