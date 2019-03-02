package com.flight.app.repo

import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import com.flight.app.model.Airport
import com.flight.app.model.City
import com.flight.app.model.FlightSchedule
import com.flight.app.service.AviationEdgeService
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

class AirportRepository
@VisibleForTesting constructor(
    @VisibleForTesting val service: AviationEdgeService
) : AirportRepositoryContract {

    constructor() : this(AviationEdgeService.getInstance())

    override fun getNearbyAirports(lat: String, lng: String, distance: String): Response<List<Airport>> {
        return try {
            service.getNearbyAirports(lat = lat, lng = lng, distance = distance).execute()
        } catch (e: IllegalStateException) {
            errorResponse(emptyList())
        }
    }

    override fun getAirportsSchedule(iataCode: String, type: String): Response<List<FlightSchedule>> {
        return try {
            service.getAirportsSchedule(iataCode = iataCode, type = type).execute()
        } catch (e: IllegalStateException) {
            errorResponse(emptyList())
        }
    }

    override fun getCity(codeIataCity: String): Response<List<City>> {
        return try {
            service.getCity(codeIataCity = codeIataCity).execute()
        } catch (e: IllegalStateException) {
            errorResponse(emptyList())
        }
    }

    companion object {

        fun <T> errorResponse(@NonNull dataType: T): Response<T> {
            return Response.error<T>(
                404, ResponseBody.create(MediaType.parse("application/json"), "{ error: { text: No Record Found } }")
            )
        }

    }

}