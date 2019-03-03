package com.flight.app.view.flightschedule

import com.flight.app.model.FlightSchedule

interface FlightScheduleView {

    fun displayError()

    fun displayEmptyData()

    fun displayFlightSchedule(flightScheduleList: List<FlightSchedule>?)

}
