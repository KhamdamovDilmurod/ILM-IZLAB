package com.example.ilm_izlab.screen.connection

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.ilm_izlab.R
import com.example.ilm_izlab.databinding.FragmentConnectionDialogBinding
import com.example.ilm_izlab.screen.splash.SplashActivity

class ConnectionDialogFragment : DialogFragment() {

    private var _binding: FragmentConnectionDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        _binding = FragmentConnectionDialogBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOK.setOnClickListener {
            val intent = Intent(requireActivity(), SplashActivity::class.java)
            startActivity(intent)
            dismiss()
        }

    }

    override fun onStart() {
        super.onStart()
        dialog!!.setCancelable(false)
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}