package com.flight.app.viewmodel

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import com.flight.app.repo.AirportRepository
import com.flight.app.view.map.MapFragmentView
import com.flight.app.viewmodel.async.FetchNearbyAirportsTask

class MapFragmentViewModel @VisibleForTesting constructor(
    @VisibleForTesting val fetchNearbyAirportsTask: FetchNearbyAirportsTask,
    application: Application
) : AndroidViewModel(application) {

    constructor(view: MapFragmentView, application: Application): this(
        FetchNearbyAirportsTask(view, AirportRepository()),
        application
    )

    fun fetchNearbyAirports(lat: String, lng: String, distance: String) {
        fetchNearbyAirportsTask.execute(lat, lng, distance)
    }

}