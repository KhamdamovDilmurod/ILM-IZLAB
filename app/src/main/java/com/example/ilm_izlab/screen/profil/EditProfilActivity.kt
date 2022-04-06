package com.example.ilm_izlab.screen.profil

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.ilm_izlab.databinding.ActivityEditProfilBinding
import com.example.ilm_izlab.screen.main.MainActivity
import com.example.ilm_izlab.screen.main.MainViewModel
import com.example.ilm_izlab.utils.Constants
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class EditProfilActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditProfilBinding

    lateinit var viewModel: MainViewModel

    var part1: MultipartBody.Part? = null
    lateinit var part2: MultipartBody.Part

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.imgBack.setOnClickListener {
            finish()
        }

        viewModel.userData.observe(this, Observer {
            binding.fullName.setText(it.fullname)
            Glide.with(this).load(Constants.HOST_IMAGE + it.avatar).into(binding.profilImage)
        })

        viewModel.progress.observe(this, Observer {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        binding.btnSave.setOnClickListener {

            part2 = MultipartBody.Part.createFormData("fullname", "${binding.fullName.text}")

            viewModel.updateAvatar(part1, part2)
            Toast.makeText(this, "Successfully changed", Toast.LENGTH_LONG).show()
        }

        binding.imageUpload.setOnClickListener {

            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }
        viewModel.updateData.observe(this, Observer {
            val i = Intent(this, MainActivity::class.java)
            startActivityForResult(i, 2002)
            finish()
        })

        viewModel.getUser()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            binding.profilImage.setImageURI(uri)

            val file: File = uri.toFile()

            // var part = MultipartBody.Part.createFormData("avatar",file.name,fileBody)

            val fileBody: RequestBody =
                file.asRequestBody("image/*".toMediaTypeOrNull())

            part1 = MultipartBody.Part.createFormData("avatar", file.name, fileBody)


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}