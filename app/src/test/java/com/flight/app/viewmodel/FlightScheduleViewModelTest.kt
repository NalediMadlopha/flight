package com.flight.app.viewmodel

import com.flight.app.model.*
import com.flight.app.repo.AirportRepository
import com.flight.app.view.flightschedule.FlightScheduleView
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import retrofit2.Response
import javax.annotation.Nullable

class FlightScheduleViewModelTest {

    private lateinit var viewModel: FlightScheduleViewModel

    @Mock
    private lateinit var mockRepository: AirportRepository
    @Mock
    private lateinit var mockView: FlightScheduleView

    @Before
    fun setUp() {
        initMocks(this)

        viewModel = FlightScheduleViewModel(mockRepository)
        viewModel.init(mockView)
    }

    @Test
    fun on_construction_the_repository_should_be_constructed() {
        assertNotNull(FlightScheduleViewModel().repository)
    }

    @Test
    fun fetchCity_should_get_city_data_from_the_repository() {
        `when`(mockRepository.getCity("")).thenReturn(successResponse(null))

        viewModel.fetchCity("")

        verify(mockRepository).getCity("")
    }

    @Test
    fun fetchCity_displayError_when_response_returned_is_not_successful() {
        `when`(mockRepository.getCity("")).thenReturn(errorResponse())

        viewModel.fetchCity("")

        verify(mockView).displayError()
    }

    @Test
    fun fetchCity_displayError_when_response_returned_successful_with_null_data() {
        `when`(mockRepository.getCity("")).thenReturn(successResponse(null))

        viewModel.fetchCity("")

        verify(mockView).displayError()
    }

    @Test
    fun fetchCity_display_city_details_when_response_returned_is_successful_with_valid_data() {
        val successResponse = successResponse(city())

        `when`(mockRepository.getCity("")).thenReturn(successResponse)

        viewModel.fetchCity("")

        successResponse.body()?.let { verify(mockView).displayAirportDetails(it) }
    }

    @Test
    fun fetchAirportSchedule_should_get_airport_schedule_data_from_the_repository() {
        `when`(mockRepository.getAirportSchedule("", "")).thenReturn(successResponse(null))

        viewModel.fetchAirportSchedule("", "")

        verify(mockRepository).getAirportSchedule("", "")
    }

    @Test
    fun fetchAirportSchedule_displayError_when_response_returned_successful_with_null_data() {
        `when`(mockRepository.getAirportSchedule("", "")).thenReturn(successResponse(null))

        viewModel.fetchAirportSchedule("", "")

        verify(mockView).displayError()
    }

    @Test
    fun getAirportSchedule_displayError_when_response_returned_is_not_successful() {
        `when`(mockRepository.getAirportSchedule("", "")).thenReturn(errorResponse())

        viewModel.fetchAirportSchedule("", "")

        verify(mockView).displayError()
    }

    @Test
    fun getAirportSchedule_displayError_when_response_returned_successful_with_null_data() {
        val successResponse = successResponse<List<FlightSchedule>>(null)

        `when`(mockRepository.getAirportSchedule("", "")).thenReturn(successResponse)

        viewModel.fetchAirportSchedule("", "")

        verify(mockView).displayError()
    }

    @Test
    fun getAirportSchedule_displayEmptyData_when_response_returned_successful_with_empty_data() {
        val successResponse = successResponse<List<FlightSchedule>>(emptyList())

        `when`(mockRepository.getAirportSchedule("", "")).thenReturn(successResponse)

        viewModel.fetchAirportSchedule("", "")

        verify(mockView).displayEmptyData()
    }

    @Test
    fun getAirportSchedule_display_flight_schedule_when_response_returned_is_successful_with_valid_data() {
        val successResponse = successResponse(listOf(flightSchedule()))

        `when`(mockRepository.getAirportSchedule("", "")).thenReturn(successResponse)

        viewModel.fetchAirportSchedule("", "")

        verify(mockView).displayFlightSchedule(successResponse.body())
    }


    companion object {

        private fun city() = City("", "", "", "",
            "", "", "", "", "", "")

        private fun flightSchedule(): FlightSchedule {
            val airline = Airline("", "", "")
            val arrival = Arrival("", "", "")
            val flight = Flight("", "", "")

            return FlightSchedule(
                airline,
                arrival,
                Codeshared(airline, flight),
                Departure("", "", "", ""),
                flight, "", ""
            )
        }


        fun <T> successResponse(@Nullable data: T?): Response<T> { return Response.success(data) }

        fun <T> errorResponse(): Response<T> {
            return Response.error<T>(
                404,
                ResponseBody.create(MediaType.parse("application/json"),
                    "{ error: { text: No Record Found } }")
            )
        }

    }

}