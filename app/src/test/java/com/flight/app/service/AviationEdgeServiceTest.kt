package com.flight.app.service

import com.flight.app.model.Airport
import com.flight.app.model.City
import com.flight.app.model.FlightSchedule
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

class AviationEdgeServiceTest {

    private lateinit var retrofit: Retrofit
    private lateinit var delegateAviationEdgeService: BehaviorDelegate<AviationEdgeService>
    private lateinit var mockRetrofit: MockRetrofit
    private lateinit var mockErrorServiceCall: MockErrorServiceCall
    private lateinit var mockSuccessServiceCall: MockSuccessServiceCall

    @Before
    fun setUp() {
        retrofit = Retrofit.Builder()
            .baseUrl(AviationEdgeService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val behavior = NetworkBehavior.create()

        mockRetrofit = MockRetrofit.Builder(retrofit).networkBehavior(behavior).build()
        delegateAviationEdgeService = mockRetrofit.create(AviationEdgeService::class.java)
        mockErrorServiceCall = MockErrorServiceCall(delegateAviationEdgeService)
        mockSuccessServiceCall = MockSuccessServiceCall(delegateAviationEdgeService)
    }

    @Test
    fun getInstance_should_return_an_instant_of_the_AviationEdgesService() {
        assertTrue(AviationEdgeService::class.java.isInstance(AviationEdgeService.getInstance()))
    }

    @Test
    fun getNearbyAirports_should_return_an_error_response_when_the_api_returns_an_error_response() {
        val call = mockErrorServiceCall.getNearbyAirports(lat = "-5.466667", lng = "122.63333", distance = "100")
        val response = call.execute()

        assertFalse(response.isSuccessful)
    }

    @Test
    fun getNearbyAirports_should_return_a_success_response_when_the_api_returns_a_success_response() {
        val call = mockSuccessServiceCall.getNearbyAirports(lat = "-5.466667", lng = "122.63333", distance = "100")
        val response = call.execute()

        assertTrue(response.isSuccessful)
    }

    @Test
    fun getAirportsSchedule_should_return_an_error_response_when_the_api_returns_an_error_response() {
        val call = mockErrorServiceCall.getAirportsSchedule(iataCode = "JFK", type = "departure")
        val response = call.execute()

        assertFalse(response.isSuccessful)
    }

    @Test
    fun getAirportsSchedule_should_return_a_success_response_when_the_api_returns_a_success_response() {
        val call = mockSuccessServiceCall.getAirportsSchedule(iataCode = "JFK", type = "departure")
        val response = call.execute()

        assertTrue(response.isSuccessful)
    }

    @Test
    fun getCity_should_return_an_error_response_when_the_api_returns_an_error_response() {
        val call = mockErrorServiceCall.getCity(codeIataCity = "JFK")
        val response = call.execute()

        assertFalse(response.isSuccessful)
    }

    @Test
    fun getCity_should_return_a_success_response_when_the_api_returns_a_success_response() {
        val call = mockSuccessServiceCall.getCity(codeIataCity = "JFK")
        val response = call.execute()

        assertTrue(response.isSuccessful)
    }

    class MockErrorServiceCall(private val service: BehaviorDelegate<AviationEdgeService>) : AviationEdgeService {

        override fun getNearbyAirports(apiKey: String, lat: String, lng: String, distance: String): Call<List<Airport>> {
            return service.returning(Calls.response(errorResponse)).getNearbyAirports(lat = lat, lng = lng, distance = distance)
        }

        override fun getAirportsSchedule(apiKey: String, iataCode: String, type: String): Call<List<FlightSchedule>> {
            return service.returning(Calls.response(errorResponse)).getAirportsSchedule(iataCode = iataCode, type = type)
        }

        override fun getCity(apiKey: String, codeIataCity: String): Call<List<City>> {
            return service.returning(Calls.response(errorResponse)).getCity(codeIataCity = codeIataCity)
        }

    }

    class MockSuccessServiceCall(private val service: BehaviorDelegate<AviationEdgeService>) : AviationEdgeService {

        override fun getNearbyAirports(apiKey: String, lat: String, lng: String, distance: String): Call<List<Airport>> {
            return service.returningResponse(Response.success(listOf<Airport>())).getNearbyAirports(apiKey, lat, lng, distance)
        }

        override fun getAirportsSchedule(apiKey: String, iataCode: String, type: String): Call<List<FlightSchedule>> {
            return service.returningResponse(Response.success(listOf<FlightSchedule>())).getAirportsSchedule(apiKey, iataCode, type)
        }

        override fun getCity(apiKey: String, codeIataCity: String): Call<List<City>> {
            return service.returningResponse(Response.success(city())).getCity(apiKey, codeIataCity)
        }

    }

    companion object {

        private fun city() = City("", "", "", "",
            "", "", "", "", "", "")

        private val errorResponse = Response.error<JsonObject>(
            404, ResponseBody.create(MediaType.parse("application/json"), "{ error: { text: No Record Found } }")
        )

    }

}