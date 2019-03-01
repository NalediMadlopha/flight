package com.flight.app.model

data class City(
    val GMT: String,
    val cityId: String,
    val codeIataCity: String,
    val codeIso2Country: String,
    val geonameId: String,
    val latitudeCity: String,
    val longitudeCity: String,
    val nameCity: String,
    val nameTranslations: String,
    val timezone: String
)