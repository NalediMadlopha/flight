package com.flight.app.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.flight.app.view.map.MapActivityView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class MapViewModel @VisibleForTesting constructor(
    private val view: MapActivityView,
    @VisibleForTesting val googleApiAvailability: GoogleApiAvailability,
    application: Application
) : AndroidViewModel(application) {

    constructor(view: MapActivityView, application: Application): this(view, GoogleApiAvailability.getInstance(), application)

    fun checkForGooglePlayServices() {
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(getApplication())

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                view.displayUserResolvableError(googleApiAvailability, resultCode)
            } else {
                view.displayNotSupportedError()
            }
            return
        }

        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            view.displayMap()
        } else {
            view.requestLocationPermission()
        }
    }

}