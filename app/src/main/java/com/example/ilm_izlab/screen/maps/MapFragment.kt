package com.example.ilm_izlab.screen.maps

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.ilm_izlab.R
import com.example.ilm_izlab.databinding.FragmentMapBinding
import com.example.ilm_izlab.model.DistrictsModel
import com.example.ilm_izlab.model.LCFilterModel
import com.example.ilm_izlab.model.SciencesModel
import com.example.ilm_izlab.model.TrainingCentersModel
import com.example.ilm_izlab.screen.edudetail.EduDetailActivity
import com.example.ilm_izlab.screen.main.MainViewModel
import com.example.ilm_izlab.screen.select.area.SelectAreaActivity
import com.example.ilm_izlab.screen.select.sciences.SelectSciencesActivity
import com.example.ilm_izlab.utils.Constants
import com.example.ilm_izlab.utils.PrefUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.Serializable
import java.util.*


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var filter: LCFilterModel

    var selectedItem: TrainingCentersModel? = null
    private lateinit var googleMap: GoogleMap

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    var locationClient: FusedLocationProviderClient? = null
    lateinit var viewModel: MainViewModel

    // result activity
    var selectedDistrict: DistrictsModel? = null

    var districtResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.data != null && result.resultCode == Activity.RESULT_OK && result.data!!.hasExtra(
                    Constants.EXTRA_DATA
                )
            ) {
                selectedDistrict =
                    result.data!!.getSerializableExtra(Constants.EXTRA_DATA) as DistrictsModel
                filter.district_id = selectedDistrict?.id ?: 0
                binding.btnRegion.text = selectedDistrict?.name_uz
                loadData()
            }
        }
    var selectedScience: SciencesModel? = null

    var sciencesResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.data != null && result.resultCode == Activity.RESULT_OK && result.data!!.hasExtra(
                    Constants.EXTRA_DATA
                )
            ) {
                selectedScience =
                    result.data!!.getSerializableExtra(Constants.EXTRA_DATA) as SciencesModel
                filter.science_id = selectedScience?.id ?: 0
                binding.btnScience.text = selectedScience?.title
                loadData()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fallasMap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        locationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filter = LCFilterModel(
            0, 0, 0, 0, "", "",
            40, 40.3680081, 71.7810391, false
        )

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        binding.btnRegion.setOnClickListener {
            val intent = Intent(requireActivity(), SelectAreaActivity::class.java)
            if (selectedDistrict != null) {
                intent.putExtra(Constants.EXTRA_DATA, selectedDistrict as Serializable)
            }
            districtResultLauncher.launch(intent)
        }

        binding.btnScience.setOnClickListener {
            val intent = Intent(requireActivity(), SelectSciencesActivity::class.java)
            if (selectedScience != null) {
                intent.putExtra(Constants.EXTRA_DATA, selectedScience as Serializable)
            }
            sciencesResultLauncher.launch(intent)
        }

        updateInfo()

        loadData()

    }

    override fun onMapReady(map: GoogleMap) {

        map.let {
            googleMap = it
            googleMap.resetMinMaxZoomPreference()
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1002
                )
                return
            }
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            googleMap.uiSettings.isZoomControlsEnabled = true
            locationClient?.lastLocation?.addOnCompleteListener {
                if(it.result == null){
                    return@addOnCompleteListener
                }
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(it.result.latitude, it.result.longitude),
                        14.0F
                    )
                )
            }
            //------------->>>>>>>>>>>>>>>
            viewModel.topTrainingCentresData.observe(viewLifecycleOwner, Observer {

                googleMap.clear()
                binding.cardInfo.visibility = View.GONE
                it.forEach {
                    googleMap.addMarker(
                        MarkerOptions().position(
                            LatLng(
                                it.latitude,
                                it.longitude
                            )
                        ).title(it.name + " id=${it.id}").snippet(it.comment)
                    )
                }
                googleMap.setOnMarkerClickListener { markerItems ->
                    selectedItem =
                        it.filter { markerItems.title == (it.name + " id=${it.id}") }.firstOrNull()
                    updateInfo()
                    false
                }
            })

            loadData()
        }

    }

    fun loadData() {
        viewModel.getTopCenters(filter)
    }

    fun updateInfo() {

        if (selectedItem != null) {

            binding.cardInfo.visibility = View.VISIBLE

            binding.tvName.text = selectedItem!!.name
            binding.tvComment.text = selectedItem!!.comment
            binding.tvRating.text = String.format("%.1f", selectedItem!!.rating)
            Glide.with(this).load(Constants.HOST_IMAGE + selectedItem!!.main_image)
                .into(binding.image)

            binding.btnNavigate.setOnClickListener {
                val uri: String = java.lang.String.format(
                    Locale.ENGLISH,
                    "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)",
                    PrefUtils.getLocation()?.latitude ?: 40.36617823333734,
                    PrefUtils.getLocation()?.longitude ?: 71.77342961588563,
                    "Home Sweet Home",
                    selectedItem!!.latitude,
                    selectedItem!!.longitude,
                    selectedItem!!.name
                )
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }
            binding.btnInfo.setOnClickListener {
                val intent = Intent(requireActivity(), EduDetailActivity::class.java)
                intent.putExtra(Constants.EXTRA_DATA, selectedItem)
                startActivity(intent)
            }
        } else {
            binding.cardInfo.visibility = View.GONE
        }
    }
}