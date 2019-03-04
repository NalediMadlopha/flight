package com.flight.app.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.flight.app.repo.AirportRepository
import com.flight.app.view.flightschedule.FlightScheduleView

class FlightScheduleViewModel @VisibleForTesting constructor(
    @VisibleForTesting val repository: AirportRepository
) : ViewModel() {

    private lateinit var view: FlightScheduleView

    constructor() : this(AirportRepository())

    fun init(view: FlightScheduleView) {
        this.view = view
    }

    // TODO: Remove this method
    fun fetchCity(codeIataCity: String) {
        val response = repository.getCity(codeIataCity)
        val data = response.body()

        if (response.isSuccessful && data != null) {
            view.displayAirportDetails(data)
        } else {
            view.displayError()
        }
    }

    fun fetchAirportSchedule(iataCode: String, type: String) {
        val response = repository.getAirportSchedule(iataCode, type)
        val data = response.body()

        if (response.isSuccessful && data != null) {
            if (data.isNotEmpty()) view.displayFlightSchedule(data) else view.displayEmptyData()
        } else {
            view.displayError()
        }
    }

}