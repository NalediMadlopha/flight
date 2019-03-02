package com.flight.app.repo

import com.flight.app.model.Airport
import com.flight.app.model.City
import com.flight.app.model.FlightSchedule
import retrofit2.Response

interface AirportRepositoryContract {

    fun getNearbyAirports(lat: String, lng: String, distance: String) : Response<List<Airport>>

    fun getAirportsSchedule(iataCode: String, type: String): Response<List<FlightSchedule>>

    fun getCity(codeIataCity: String): Response<List<City>>

}