package com.flight.app.repo

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
        `when`(mockService.getNearbyAirports(lat = LAT, lng = LNG, distance = DISTANCE)).thenReturn(mockAirportListCall)

        repository.getNearbyAirports(LAT, LNG, DISTANCE)

        verify(mockAirportListCall).execute()
    }

    @Test
    fun getNearbyAirports_should_return_an_error_response_when_the_service_returns_an_error_response() {
        `when`(mockService.getNearbyAirports(lat = LAT, lng = LNG, distance = DISTANCE)).thenReturn(mockAirportListCall)
        `when`(mockAirportListCall.execute()).thenReturn(errorResponse())

        val response = repository.getNearbyAirports(LAT, LNG, DISTANCE)

        assertFalse(response.isSuccessful)
    }

    @Test
    fun getNearbyAirports_should_return_a_success_response_when_the_service_returns_a_success_response() {
        `when`(mockService.getNearbyAirports(lat = LAT, lng = LNG, distance = DISTANCE)).thenReturn(mockAirportListCall)
        `when`(mockAirportListCall.execute()).thenReturn(Response.success(listOf()))

        val response = repository.getNearbyAirports(LAT, LNG, DISTANCE)

        assertTrue(response.isSuccessful)
    }

    @Test
    fun getAirportsSchedule_should_execute_a_call_to_the_service() {
        `when`(mockService.getAirportsSchedule(iataCode = IATA_CODE, type = TYPE)).thenReturn(mockFlightScheduleListCall)

        repository.getAirportSchedule(IATA_CODE, TYPE)

        verify(mockFlightScheduleListCall).execute()
    }

    @Test
    fun getAirportsSchedule_should_return_an_error_response_when_the_service_returns_an_error_response() {
        `when`(mockService.getAirportsSchedule(iataCode = IATA_CODE, type = TYPE)).thenReturn(mockFlightScheduleListCall)
        `when`(mockFlightScheduleListCall.execute()).thenReturn(errorResponse())

        val response = repository.getAirportSchedule(IATA_CODE, TYPE)

        assertFalse(response.isSuccessful)
    }

    @Test
    fun getAirportsSchedule_should_return_a_success_response_when_the_service_returns_a_success_response() {
        `when`(mockService.getAirportsSchedule(iataCode = IATA_CODE, type = TYPE)).thenReturn(mockFlightScheduleListCall)
        `when`(mockFlightScheduleListCall.execute()).thenReturn(Response.success(listOf()))

        val response = repository.getAirportSchedule(IATA_CODE, TYPE)

        assertTrue(response.isSuccessful)
    }

    @Test
    fun getCity_should_execute_a_call_to_the_service() {
        `when`(mockService.getCity(codeIataCity = CODE_IATA_CITY)).thenReturn(mockCityListCall)

        repository.getCity(CODE_IATA_CITY)

        verify(mockCityListCall).execute()
    }

    @Test
    fun getCity_should_return_an_error_response_when_the_service_returns_an_error_response() {
        `when`(mockService.getCity(codeIataCity = CODE_IATA_CITY)).thenReturn(mockCityListCall)
        `when`(mockCityListCall.execute()).thenReturn(errorResponse())

        val response = repository.getCity(CODE_IATA_CITY)

        assertFalse(response.isSuccessful)
    }

    @Test
    fun getCity_should_return_a_success_response_when_the_service_returns_a_success_response() {
        `when`(mockService.getCity(codeIataCity = CODE_IATA_CITY)).thenReturn(mockCityListCall)
        `when`(mockCityListCall.execute()).thenReturn(Response.success(listOf()))

        val response = repository.getCity(CODE_IATA_CITY)

        assertTrue(response.isSuccessful)
    }

    companion object {
        private const val IATA_CODE = "JFK"
        private const val TYPE = "departure"
        private const val CODE_IATA_CITY = "JFK"
        private const val LAT = "-26.1304"
        private const val LNG = "28.2096"
        private const val DISTANCE = "5000.0"

        fun <T> errorResponse(): Response<T> {
            return Response.error<T>(
                404, ResponseBody.create(MediaType.parse("application/json"), "{ error: { text: No Record Found } }")
            )
        }

    }
}