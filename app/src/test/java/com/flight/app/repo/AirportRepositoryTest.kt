package com.flight.app.repo

import androidx.annotation.NonNull
import com.flight.app.model.Airport
import com.flight.app.model.City
import com.flight.app.model.FlightSchedule
import com.flight.app.service.AviationEdgeService
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import retrofit2.Call
import retrofit2.Response

class AirportRepositoryTest {

    private lateinit var repository: AirportRepository

    @Mock
    private lateinit var mockService: AviationEdgeService
    @Mock
    private lateinit var mockAirportListCall: Call<List<Airport>>
    @Mock
    private lateinit var mockFlightScheduleListCall: Call<List<FlightSchedule>>
    @Mock
    private lateinit var mockCityListCall: Call<List<City>>

    @Before
    fun setUp() {
        initMocks(this)

        repository = AirportRepository(mockService)
    }

    @Test
    fun on_constructon_verify_that_the_service_is_initialized() {
        assertNotNull(AirportRepository().service)
    }

    @Test
    fun getNearbyAirports_should_execute_a_call_to_the_service() {
        val lat = "-26.1304"; val lng = "28.2096"; val distance = "5000.0"

        `when`(mockService.getNearbyAirports(lat = lat, lng = lng, distance = distance)).thenReturn(mockAirportListCall)

        repository.getNearbyAirports(lat, lng, distance)

        verify(mockAirportListCall).execute()
    }

    @Test
    fun getNearbyAirports_should_return_an_error_response_when_the_service_returns_an_error_response() {
        val lat = "-26.1304"; val lng = "28.2096"; val distance = "5000.0"

        `when`(mockService.getNearbyAirports(lat = lat, lng = lng, distance = distance)).thenReturn(mockAirportListCall)
        `when`(mockAirportListCall.execute()).thenReturn(errorResponse(listOf()))

        val response = repository.getNearbyAirports(lat, lng, distance)

        assertFalse(response.isSuccessful)
    }

    @Test
    fun getNearbyAirports_should_return_a_success_response_when_the_service_returns_a_success_response() {
        val lat = "-26.1304"; val lng = "28.2096"; val distance = "5000.0"

        `when`(mockService.getNearbyAirports(lat = lat, lng = lng, distance = distance)).thenReturn(mockAirportListCall)
        `when`(mockAirportListCall.execute()).thenReturn(Response.success(listOf()))

        val response = repository.getNearbyAirports(lat, lng, distance)

        assertTrue(response.isSuccessful)
    }

    @Test
    fun getAirportsSchedule_should_execute_a_call_to_the_service() {
        val iataCode = "JFK"; val type = "departure"

        `when`(mockService.getAirportsSchedule(iataCode = iataCode, type = type)).thenReturn(mockFlightScheduleListCall)

        repository.getAirportsSchedule(iataCode, type)

        verify(mockFlightScheduleListCall).execute()
    }

    @Test
    fun getAirportsSchedule_should_return_an_error_response_when_the_service_returns_an_error_response() {
        val iataCode = "JFK"; val type = "departure"

        `when`(mockService.getAirportsSchedule(iataCode = iataCode, type = type)).thenReturn(mockFlightScheduleListCall)
        `when`(mockFlightScheduleListCall.execute()).thenReturn(errorResponse(listOf()))

        val response = repository.getAirportsSchedule(iataCode, type)

        assertFalse(response.isSuccessful)
    }

    @Test
    fun getAirportsSchedule_should_return_a_success_response_when_the_service_returns_a_success_response() {
        val iataCode = "JFK"; val type = "departure"

        `when`(mockService.getAirportsSchedule(iataCode = iataCode, type = type)).thenReturn(mockFlightScheduleListCall)
        `when`(mockFlightScheduleListCall.execute()).thenReturn(Response.success(listOf()))

        val response = repository.getAirportsSchedule(iataCode, type)

        assertTrue(response.isSuccessful)
    }

    @Test
    fun getCity_should_execute_a_call_to_the_service() {
        val codeIataCity = "JFK"

        `when`(mockService.getCity(codeIataCity = codeIataCity)).thenReturn(mockCityListCall)

        repository.getCity(codeIataCity)

        verify(mockCityListCall).execute()
    }

    @Test
    fun getCity_should_return_an_error_response_when_the_service_returns_an_error_response() {
        val codeIataCity = "JFK"

        `when`(mockService.getCity(codeIataCity = codeIataCity)).thenReturn(mockCityListCall)
        `when`(mockCityListCall.execute()).thenReturn(errorResponse(listOf()))

        val response = repository.getCity(codeIataCity)

        assertFalse(response.isSuccessful)
    }

    @Test
    fun getCity_should_return_a_success_response_when_the_service_returns_a_success_response() {
        val codeIataCity = "JFK"

        `when`(mockService.getCity(codeIataCity = codeIataCity)).thenReturn(mockCityListCall)
        `when`(mockCityListCall.execute()).thenReturn(Response.success(listOf()))

        val response = repository.getCity(codeIataCity)

        assertTrue(response.isSuccessful)
    }

    companion object {

        fun <T> errorResponse(@NonNull dataType: T): Response<T> {
            return Response.error<T>(
                404, ResponseBody.create(MediaType.parse("application/json"), "{ error: { text: No Record Found } }")
            )
        }

    }
}