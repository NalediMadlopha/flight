package com.flight.app.model

data class FlightSchedule(
    val airline: Airline,
    val arrival: Arrival,
    val codeshared: Codeshared,
    val departure: Departure,
    val flight: Flight,
    val status: String,
    val type: String
)

data class Airline(
    val iataCode: String,
    val icaoCode: String,
    val name: String
)

data class Arrival(
    val iataCode: String,
    val icaoCode: String,
    val scheduledTime: String
)

data class Departure(
    val iataCode: String,
    val icaoCode: String,
    val scheduledTime: String,
    val terminal: String
)

data class Codeshared(
    val airline: Airline,
    val flight: Flight
)

data class Flight(
    val iataNumber: String,
    val icaoNumber: String,
    val number: String
)