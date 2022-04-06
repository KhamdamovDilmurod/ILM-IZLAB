package com.example.ilm_izlab.screen.control.addCenters

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.ilm_izlab.databinding.ActivityAddCenterBinding
import com.example.ilm_izlab.screen.control.ControlCentersActivity
import com.example.ilm_izlab.screen.main.MainViewModel
import com.example.ilm_izlab.utils.PrefUtils
import com.github.dhaval2404.imagepicker.ImagePicker

class AddCenterActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddCenterBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainViewModel()
        binding = ActivityAddCenterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            startActivity(Intent(this, ControlCentersActivity::class.java))
            finish()
        }

        viewModel.getRegions()
        val popupRegion = PopupMenu(this, binding.edRegion)
        val popupDistrict = PopupMenu(this, binding.edDistrict)
        viewModel.regionsData.observe(this, Observer {
            val menu = popupRegion.menu
            it.forEach { menu.add(it.id, it.id, it.id, it.name_uz) }
            popupRegion.setOnMenuItemClickListener { menuItem ->
                val i = menuItem.itemId
                binding.edRegion.setText(it[i-1].name_uz)
                return@setOnMenuItemClickListener true
            }
        })

        binding.edRegion.setOnClickListener {
            popupRegion.show()
        }

        binding.pickerImage.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()

            //---------------------
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            binding.image.visibility = View.VISIBLE
            binding.image.setImageURI(uri)
            PrefUtils.setProfilImage(uri.path.toString())
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

}