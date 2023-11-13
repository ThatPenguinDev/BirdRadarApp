// MapFragment.kt
package com.example.loginpage

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var btnRecenter: ImageButton
    private lateinit var btnLayer: ImageButton
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var isSatelliteView = false

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Initialize UI components
        btnRecenter = view.findViewById(R.id.btnRecenter)
        btnLayer = view.findViewById(R.id.btnLayer)

        // Set up the map
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Set up click listeners
        btnRecenter.setOnClickListener { recenterMap() }
        btnLayer.setOnClickListener { showLayerOptions() }

        return view
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Check for location permissions
        if (isLocationPermissionGranted()) {
            // Enable user's location
            googleMap.isMyLocationEnabled = true

            // Initialize FusedLocationProviderClient
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

            // Get the last known location
            try {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        // Check if the location is not null
                        if (location != null) {
                            // Move camera to user's current location
                            val userLocation = LatLng(location.latitude, location.longitude)
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                        }
                    }
            } catch (securityException: SecurityException) {
                securityException.printStackTrace()
            }
        } else {
            // Request location permission
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun recenterMap() {
        // Move camera back to user's location
        // You can get the updated user location using location services
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    // Check if the location is not null
                    if (location != null) {
                        // Move camera to user's current location
                        val userLocation = LatLng(location.latitude, location.longitude)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                    }
                }
        } catch (securityException: SecurityException) {
            securityException.printStackTrace()
        }
    }

    private fun showLayerOptions() {
        // Toggle between satellite and normal map types
        if (isSatelliteView) {
            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        } else {
            googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        }

        // Update the flag
        isSatelliteView = !isSatelliteView
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, initialize the map
                    onMapReady(googleMap)
                } else {
                    // Permission denied, handle accordingly
                    // You may show a dialog to the user explaining why you need the permission and then request it again
                }
            }
        }
    }
}