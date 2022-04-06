package com.example.ilm_izlab.screen.subscribe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ilm_izlab.R
import com.example.ilm_izlab.adapters.NearTrainingCentersAdapter
import com.example.ilm_izlab.adapters.TrainingCentersAdapter
import com.example.ilm_izlab.databinding.FragmentSubscribeBinding
import com.example.ilm_izlab.databinding.FragmentVibrationBinding
import com.example.ilm_izlab.model.LCFilterModel
import com.example.ilm_izlab.screen.main.MainViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SubscribeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSubscribeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private lateinit var filter: LCFilterModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentSubscribeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipe.isRefreshing = false
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.topTrainingCentresData.observe(requireActivity(), Observer {

            binding.recyclerSubscriber.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recyclerSubscriber.adapter = NearTrainingCentersAdapter(it)

        })

    }
    fun loadData() {
        filter = LCFilterModel(
            0,0,0,0,"","rating",
            40,40.3680081,71.7810391,true
        )
        viewModel.getTopCenters(filter)

    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = SubscribeFragment()
    }
}