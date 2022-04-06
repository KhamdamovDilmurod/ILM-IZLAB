package com.example.ilm_izlab.screen.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.ilm_izlab.R
import com.example.ilm_izlab.screen.connection.ConnectionDialogFragment
import com.example.ilm_izlab.screen.main.MainActivity
import com.example.ilm_izlab.utils.CheckNetworkConnection
import com.example.ilm_izlab.utils.GpsUtils

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var mLocationManager: LocationManager? = null

    private val TAG = "SPLASH_ACTIVITY"

    private lateinit var checkNetworkConnection: CheckNetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        supportActionBar?.hide()

        callNetworkConnection()

    }

    private fun callNetworkConnection() {
        checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection.observe(this) { isConnected ->
            if (isConnected) {
                if (mLocationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true){
                    Handler().postDelayed({
                        finish()
                        startActivity(Intent(this, MainActivity::class.java))
                    }, 2000)
                }else{
                    GpsUtils(this).enableLocationSettings(this)
                }
            } else {
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1) {
            Log.d(TAG, "onActivityResult:SUCCESS")
            Handler().postDelayed({
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }, 2000)
        } else {
            GpsUtils(this).enableLocationSettings(this)
        }
    }
}