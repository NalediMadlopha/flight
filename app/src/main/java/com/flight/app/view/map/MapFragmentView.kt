package com.flight.app.view.map

import androidx.annotation.MainThread
import com.flight.app.model.Airport

interface MapFragmentView {

    @MainThread
    fun displayAirportMapMarkers(airportList: List<Airport>)

    fun displayError()

    fun displayNoAirportNearby()

}
