package com.flight.app.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flight.app.repo.AirportRepository
import com.flight.app.view.flightschedule.FlightScheduleView
import com.flight.app.viewmodel.async.FetchAirportScheduleTask
import com.flight.app.viewmodel.async.FetchCityTask

class FlightScheduleViewModel @VisibleForTesting constructor(
    @VisibleForTesting val repository: AirportRepository
) : ViewModel() {

    private var airportName = MutableLiveData<String>()
    private var codeIataCity = MutableLiveData<String>()
    private var codeIataAirport = MutableLiveData<String>()

    private lateinit var view: FlightScheduleView
    private lateinit var type: String

    constructor() : this(AirportRepository())

    fun init(view: FlightScheduleView) {
        this.view = view
    }

    fun fetchCity() {
        FetchCityTask(view).execute(codeIataCity.value)
    }

    fun fetchAirportSchedule() {
        FetchAirportScheduleTask(view).execute(codeIataAirport.value, type)
    }

    fun setAirportDetails(airportName: MutableLiveData<String>, iataCode: MutableLiveData<String>, type: String) {
        this.airportName = airportName

        this.codeIataCity.value = iataCode.value?.substringBefore('|')
        this.codeIataAirport.value = iataCode.value?.substringAfter('|')
        this.type = type
    }

    fun getAirportName(): String {
        return this.airportName.value!!
    }

}