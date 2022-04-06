package com.example.ilm_izlab.screen.fragments.tablayout.kurslar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ilm_izlab.adapters.CoursesAdapter
import com.example.ilm_izlab.databinding.FragmentCoursesBinding
import com.example.ilm_izlab.model.TrainingCentersModel
import com.example.ilm_izlab.screen.main.MainViewModel

class CoursesFragment(var item: TrainingCentersModel) : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCoursesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.coursesRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity())
        binding.coursesRecyclerView.adapter = CoursesAdapter(item.courses)

    }

}