package com.flight.app.view.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.flight.app.R
import com.flight.app.viewmodel.MapViewModel
import com.flight.app.viewmodel.ViewModelFactory
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.maps_activity.*

class MapsActivity : AppCompatActivity(), MapActivityView {

    private lateinit var viewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.flight.app.R.layout.maps_activity)

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory { MapViewModel(this, application) }
        ).get(MapViewModel::class.java)

        viewModel.checkForGooglePlayServices()
    }

    override fun displayUserResolvableError(googleApiAvailability: GoogleApiAvailability, resultCode: Int) {
        googleApiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show()
    }

    override fun displayNotSupportedError() {
        mapContainer.visibility = View.GONE
        deviceNotSupportedErrorTextView.visibility = View.VISIBLE
    }

    override fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSIONS_REQUEST_CODE)
    }

    override fun displayMap() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.mapContainer, MapFragment())
            .commit()

        deviceNotSupportedErrorTextView.visibility = View.GONE
        locationPermissionNotGrantedErrorTextView.visibility = View.GONE
        mapContainer.visibility = View.VISIBLE
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSIONS_REQUEST_CODE -> {
                if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                    displayMap()
                } else {
                    mapContainer.visibility = View.GONE
                    deviceNotSupportedErrorTextView.visibility = View.GONE
                    locationPermissionNotGrantedErrorTextView.visibility = View.VISIBLE
                }
            }
        }

    }

    companion object {
        private const val LOCATION_PERMISSIONS_REQUEST_CODE = 0
        private const val PLAY_SERVICES_RESOLUTION_REQUEST = 9000
    }

}
