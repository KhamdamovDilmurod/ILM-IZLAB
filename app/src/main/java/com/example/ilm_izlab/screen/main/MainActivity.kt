package com.example.ilm_izlab.screen.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.ilm_izlab.R
import com.example.ilm_izlab.databinding.ActivityMainBinding
import com.example.ilm_izlab.model.LocationModel
import com.example.ilm_izlab.screen.connection.ConnectionDialogFragment
import com.example.ilm_izlab.screen.control.ControlCentersActivity
import com.example.ilm_izlab.screen.home.HomeFragment
import com.example.ilm_izlab.screen.ilovahaqida.AboutApp
import com.example.ilm_izlab.screen.maps.MapFragment
import com.example.ilm_izlab.screen.news.NewsFragment
import com.example.ilm_izlab.screen.profil.EditProfilActivity
import com.example.ilm_izlab.screen.search.SearchFragment
import com.example.ilm_izlab.screen.sign.LoginActivity
import com.example.ilm_izlab.screen.splash.SplashActivity
import com.example.ilm_izlab.screen.subscribe.SubscribeFragment
import com.example.ilm_izlab.utils.CheckNetworkConnection
import com.example.ilm_izlab.utils.Constants
import com.example.ilm_izlab.utils.PrefUtils
import com.google.android.gms.location.*


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var passed = false

    lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private lateinit var checkNetworkConnection: CheckNetworkConnection

    val homeFragment = HomeFragment()
    val searchFragment = SearchFragment()
    val subscribeFragment = SubscribeFragment()
    val vibrationFragment =NewsFragment()
    val locationFragment = MapFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

       binding.btnGetLocation.setOnClickListener {
            getLocation()
       }
        getLocation()

        var activeFragment: Fragment = homeFragment

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_container, homeFragment, homeFragment.tag)
            .hide(homeFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_container, searchFragment, searchFragment.tag)
            .hide(searchFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_container, subscribeFragment, subscribeFragment.tag)
            .hide(subscribeFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_container, vibrationFragment, vibrationFragment.tag)
            .hide(vibrationFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_container, locationFragment, locationFragment.tag)
            .hide(locationFragment).commit()

        supportFragmentManager.beginTransaction().show(activeFragment).addToBackStack(null).commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.home) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).addToBackStack(null)
                    .commit()
                activeFragment = homeFragment
            } else if (it.itemId == R.id.search) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(searchFragment).addToBackStack(null).commit()
                activeFragment = searchFragment
            } else if (it.itemId == R.id.location) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(locationFragment).addToBackStack(null).commit()
                activeFragment = locationFragment
            } else if (it.itemId == R.id.amp) {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(vibrationFragment).addToBackStack(null).commit()
                    activeFragment = vibrationFragment
            }else if (it.itemId == R.id.subscribe){
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(subscribeFragment).addToBackStack(null).commit()
                activeFragment = subscribeFragment
            }
            return@setOnItemSelectedListener true
        }

        binding.menu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        initViews()

        binding.aboutApp.setOnClickListener {
            val intent = Intent(this,AboutApp::class.java)
            startActivity(intent)
            finish()
        }

        binding.enterSignIn.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.controlCenters.setOnClickListener {
            val intent = Intent(this,ControlCentersActivity::class.java)
            startActivity(intent)
        }

        binding.editProfil.setOnClickListener {
            val intent = Intent(this,EditProfilActivity::class.java)
            startActivity(intent)
        }
        binding.logOut.setOnClickListener {
            val builder  = AlertDialog.Builder(this)
                .setTitle("ILM-IZLAB")
                .setCancelable(false)
                .setMessage("Rostdan ham tizimdan chiqishni istaysizmi?")
                .setNegativeButton("Yo'q"){ _, _ ->

                }
                .setPositiveButton("Ha") { _, _ ->
                    PrefUtils.clear()
                    initViews()
                }
            val dialog = builder.create()
            dialog.show()

        }
        binding.contactWithUs.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://t.me/Hamdamov_Dilmurod")
            startActivity(intent)
        }

        viewModel.userData.observe(this, Observer {
            binding.userName.text = it.fullname
            binding.phone.text = it.phone
            Glide.with(this).load(Constants.HOST_IMAGE + it.avatar).into(binding.profilImage)
        })

        viewModel.getUser()
        callNetworkConnection()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==2002){
            loadData()
        }
    }

    private fun loadData() {
        viewModel.getUser()
    }

    private fun initViews(){
        if(PrefUtils.getToken().isNullOrEmpty()){
            //--->> VISIBLE
            binding.enterSignIn.visibility = View.VISIBLE

            //--->> GONE
            binding.profil.visibility = View.GONE
            binding.editProfil.visibility = View.GONE
            binding.controlCenters.visibility = View.GONE
            binding.logOut.visibility = View.GONE

        }else{
            //--->> VISIBLE

            binding.profil.visibility = View.VISIBLE
            binding.editProfil.visibility = View.VISIBLE
            binding.controlCenters.visibility = View.VISIBLE
            binding.logOut.visibility = View.VISIBLE

            //--->> GONE
            binding.enterSignIn.visibility = View.GONE
        }
    }

    private fun getLocation(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            if(location!=null){
                Toast.makeText(
                    this,
                    "Joylashuv aniqlandi",
                    Toast.LENGTH_SHORT
                ).show()

                val currentLocation = LocationModel(location.latitude,location.longitude)

                PrefUtils.setLocation(currentLocation)
            }else{
                Toast.makeText(
                    this,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun callNetworkConnection() {
        checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection.observe(this) { isConnected ->
            if (isConnected) {
            } else {
                ConnectionDialogFragment().show(supportFragmentManager, "CheckConnection")
            }
        }

    }

    override fun onBackPressed() {
        if (passed) {
            super.onBackPressed()
        }
        Toast.makeText(this, "Chiqish uchun yana bir marta bosing", Toast.LENGTH_SHORT).show()
        passed = true
        val handler = Handler()
        handler.postDelayed({
            passed = false
        },2000)
    }

}
