package com.mobile.liderstoreagent.ui.screens.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.ui.dialogs.ApplyDialog
import com.mobile.liderstoreagent.ui.viewmodels.MapViewModel
import com.mobile.liderstoreagent.utils.checkPermissions
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_maps.*


@Suppress("DEPRECATION")
class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    private val viewModel : MapViewModel by viewModels()
    private lateinit var centerLocation : LatLng
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest : LocationRequest
    private var locationUpdateState = false
    private val REQUEST_CHECK_SETTINGS = 2
    private var isEdit = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationRequest = LocationRequest()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                viewModel.fineUserLocation(p0.lastLocation)
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(42.00, 66.00), 5f))

        viewModel.fineUserLocationLiveData.observe(this,fineUserLocationObserver)
        viewModel.permissionLiveData.observe(this,permissionObserver)
        viewModel.showDialogLiveData.observe(this,showDialogObserver)
        viewModel.closeScreenLiveData.observe(this,closeObserver)
        viewModel.gerPermission(mMap)
        buttonApply.setOnClickListener {
            centerLocation = mMap.projection.visibleRegion.latLngBounds.center
            viewModel.send("${centerLocation.latitude};${centerLocation.longitude}")
        }
        buttonBack.setOnClickListener { viewModel.closeScreen() }
    }

    private val showDialogObserver = Observer<Unit> {
        val dialog = ApplyDialog()
        dialog.show(childFragmentManager,"applyDialog")
        dialog.clickApply {
            dialog.dismiss()
            viewModel.send("${centerLocation.latitude};${centerLocation.longitude}")
        }
    }

    private val editLocationObserver = Observer<String?> {

    }
    private val closeObserver = Observer<Unit> { findNavController().navigateUp() }
    private val fineUserLocationObserver = Observer<Location>{
        if (!isEdit) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude),12.0f))
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    @SuppressLint("MissingPermission")
    private val permissionObserver = Observer<GoogleMap> {
        requireActivity().checkPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)) {
            it.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                if(location != null){
                    viewModel.fineUserLocation(location)
                }
            }
            createLocationRequest()
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun createLocationRequest() {
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(requireContext())
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }

        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

}