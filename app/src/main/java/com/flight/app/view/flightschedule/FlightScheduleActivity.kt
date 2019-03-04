package com.flight.app.view.flightschedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.flight.app.R
import com.flight.app.viewmodel.FlightScheduleViewModel
import com.flight.app.viewmodel.ViewModelFactory

class FlightScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flight_schedule_activity)

        val airportName = MutableLiveData<String>()
        airportName.value = intent.getStringExtra((SELECTED_AIRPORT_NAME))

        val iataCode = MutableLiveData<String>()
        iataCode.value = intent.getStringExtra((SELECTED_IATA_CODE))

        val viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory { FlightScheduleViewModel() }
        ).get(FlightScheduleViewModel::class.java)

        viewModel.setAirportDetails(airportName, iataCode, "departure")

    }

    companion object {
        private const val SELECTED_AIRPORT_NAME = "SELECTED_AIRPORT_NAME"
        private const val SELECTED_IATA_CODE = "SELECTED_IATA_CODE"
    }

}