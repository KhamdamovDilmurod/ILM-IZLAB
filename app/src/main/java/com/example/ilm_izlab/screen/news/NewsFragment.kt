package com.example.ilm_izlab.screen.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ilm_izlab.adapters.NewsAdapter
import com.example.ilm_izlab.databinding.FragmentVibrationBinding
import com.example.ilm_izlab.screen.main.MainViewModel
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NewsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentVibrationBinding? = null
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentVibrationBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        viewModel.allNewsData.observe(requireActivity(), Observer {
            binding.recyclerVibration.layoutManager =
                LinearLayoutManager(requireActivity())
            binding.recyclerVibration.adapter = NewsAdapter(it)
        })
        loadData()
    }
    fun loadData() {
        viewModel.getAllNews()
    }

    companion object {

        @JvmStatic fun newInstance(param1: String, param2: String) =
                NewsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }

    }

}