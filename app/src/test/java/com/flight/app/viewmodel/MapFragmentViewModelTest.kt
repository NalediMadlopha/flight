package com.flight.app.viewmodel

import android.app.Application
import com.flight.app.repo.AirportRepository
import com.flight.app.view.map.MapFragmentView
import com.flight.app.viewmodel.async.FetchNearbyAirportsTask
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks

class MapFragmentViewModelTest {

    private lateinit var viewModel: MapFragmentViewModel

    @Mock
    private lateinit var mockView: MapFragmentView
    @Mock
    private lateinit var mockFetchNearbyAirportsTask: MockFetchNearbyAirportsTask
    @Mock
    private lateinit var mockApplication: Application

    @Before
    fun setUp() {
        initMocks(this)

        viewModel = MapFragmentViewModel(mockFetchNearbyAirportsTask, mockApplication)
    }

    @Test
    fun on_construction_the_fetchNearbyAirportsTask_should_be_constructed() {
        assertNotNull(MapFragmentViewModel(mockView, mockApplication).fetchNearbyAirportsTask)
    }

    @Test
    fun fetchNearbyAirports_should_execute_fetchNearbyAirportsTask() {
        val lat = "lat"
        val lng = "lng"
        val distance = "distance"

        viewModel.fetchNearbyAirports(lat, lng, distance)

        verify(mockFetchNearbyAirportsTask).execute(lat, lng, distance)
    }

    open class MockFetchNearbyAirportsTask(view: MapFragmentView, repository: AirportRepository)
        : FetchNearbyAirportsTask(view, repository)

}