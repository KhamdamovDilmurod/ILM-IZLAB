package com.example.ilm_izlab.screen.select.type

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.ilm_izlab.R
import com.example.ilm_izlab.databinding.FragmentSelectBinding
import com.example.ilm_izlab.screen.main.MainActivity
import com.example.ilm_izlab.screen.search.SearchFragment

class SelectFragment: DialogFragment() {

    private var _binding: FragmentSelectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)

        _binding = FragmentSelectBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvMain.setOnClickListener {
            startActivity(Intent(requireContext(),MainActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        dialog!!.setCancelable(false)
        val width = (resources.displayMetrics.widthPixels * 0.8).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.4).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}