package com.example.ilm_izlab.screen.evaluation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ilm_izlab.R
import com.example.ilm_izlab.databinding.ActivityEvaluationBinding
import com.example.ilm_izlab.model.MakeRatingRequest
import com.example.ilm_izlab.model.TrainingCentersModel
import com.example.ilm_izlab.model.response.LoginResponse
import com.example.ilm_izlab.model.response.RegistrationModel
import com.example.ilm_izlab.screen.main.MainViewModel
import com.example.ilm_izlab.screen.select.type.SelectFragment
import com.example.ilm_izlab.screen.sign.LoginActivity
import com.example.ilm_izlab.utils.Constants
import com.example.ilm_izlab.utils.PrefUtils

class EvaluationActivity : AppCompatActivity() {

    lateinit var binding: ActivityEvaluationBinding

    lateinit var viewModel: MainViewModel

    lateinit var item: TrainingCentersModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEvaluationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.progress.observe(this, Observer {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.makeRatingData.observe(this, Observer {
            SelectFragment().show(supportFragmentManager, "MyCustomFragment")
        })

        item = intent.getSerializableExtra(Constants.EXTRA_DATA) as TrainingCentersModel

        binding.centerName.text = item.name

        if(PrefUtils.getToken().isNullOrEmpty()){
            //--->> VISIBLE
            binding.lySignIn.visibility = View.VISIBLE
            //--->> GONE
            binding.btnSend.visibility = View.GONE

        }else{
            //--->> VISIBLE
            binding.btnSend.visibility = View.VISIBLE
            //--->> GONE
            binding.lySignIn.visibility = View.GONE
        }

        binding.btnSend.setOnClickListener {
            val rating = binding.ratingBar.rating
            val comment =binding.editComment.text.toString()
            val centerId = item.id
            val makeRatingRequest = MakeRatingRequest(rating,comment,centerId)
            viewModel.makeRating(makeRatingRequest)

        }
        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }
}