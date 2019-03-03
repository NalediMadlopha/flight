package com.flight.app.view.map

import com.google.android.gms.common.GoogleApiAvailability

interface MapActivityView {

    fun displayUserResolvableError(
        googleApiAvailability: GoogleApiAvailability,
        resultCode: Int
    )

    fun displayNotSupportedError()

    fun requestLocationPermission()

    fun displayMap()

}
