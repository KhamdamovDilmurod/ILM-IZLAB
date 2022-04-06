package com.example.ilm_izlab.screen.show_fully

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ilm_izlab.databinding.FragmentShowFullyComentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ShowFullyCommentFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentShowFullyComentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShowFullyComentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShowFullyCommentFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = arguments
        binding.tvComment.text = arguments?.getString("comment")

    }
}