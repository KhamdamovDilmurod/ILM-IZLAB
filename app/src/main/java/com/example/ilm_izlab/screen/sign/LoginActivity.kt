package com.example.ilm_izlab.screen.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ilm_izlab.databinding.ActivityLoginBinding
import com.example.ilm_izlab.model.request.CheckPhoneRequest
import com.example.ilm_izlab.model.request.LoginRequest
import com.example.ilm_izlab.model.request.RegistrationRequest
import com.example.ilm_izlab.screen.main.MainActivity
import com.example.ilm_izlab.screen.main.MainViewModel
import com.example.ilm_izlab.utils.PrefUtils


enum class LoginState{
    CHECK_PHONE,
    LOGIN,
    REGISTRATION
}

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: MainViewModel
    var state = LoginState.CHECK_PHONE
    var phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(this, Observer {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.checkPhoneData.observe(this, Observer {
            if (it.isReg){
                state = LoginState.LOGIN
            }else{
                state = LoginState.REGISTRATION
            }
            initViews()
        })

        viewModel.registrationData.observe(this, Observer {
            PrefUtils.setToken(it.token)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        viewModel.loginData.observe(this, Observer {
            //PrefUtils.setUser(it)
            PrefUtils.setToken(it.token)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.btnNext.setOnClickListener {
            when(state){
                LoginState.CHECK_PHONE ->{
                    phone = binding.edPhone.text.toString().replace(" ", "")
                    val checkPhoneRequest = CheckPhoneRequest(phone)
                    viewModel.checkPhone(checkPhoneRequest)
                }
                LoginState.LOGIN->{
                    val loginRequest = LoginRequest(phone,binding.edPassword.text.toString())
                    viewModel.login(loginRequest)
                }
                LoginState.REGISTRATION->{
                    val fullname = binding.edFullname.text.toString()
                    val password = binding.edPassword.text.toString()
                    val repassword = binding.edRePassword.text.toString()
                    if (fullname.isNullOrEmpty()){
                        Toast.makeText(this, "Iltimos ismingizni kiriting!", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    if (password.isNullOrEmpty()){
                        Toast.makeText(this, "Iltimos parolni kiriting!", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    if (password != repassword){
                        Toast.makeText(this, "Iltimos parolni to'g'ri tasdiqlang!", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    val registrationRequest = RegistrationRequest(phone,password,"1111",fullname)
                    viewModel.registration(registrationRequest)
                }
            }
        }

        initViews()
    }

    fun initViews(){
        binding.lyFullname.visibility = View.GONE
        binding.lySMSCode.visibility = View.GONE
        binding.lyPassword.visibility = View.GONE
        binding.lyRePassword.visibility = View.GONE

        when(state){
            LoginState.CHECK_PHONE ->{
                binding.tvTitle.text = "Tizimga kirish"
                binding.edPhone.isEnabled = true
            }
            LoginState.LOGIN ->{
                binding.tvTitle.text = "Tizimga kirish"
                binding.lyPassword.visibility = View.VISIBLE
                binding.edPhone.isEnabled = false
            }
            LoginState.REGISTRATION ->{
                binding.tvTitle.text = "Ro'yxatdan o'tish"
                binding.lyFullname.visibility = View.VISIBLE
                binding.lyPassword.visibility = View.VISIBLE
                binding.lyRePassword.visibility = View.VISIBLE
                binding.edPhone.isEnabled = false
            }
        }
    }
}