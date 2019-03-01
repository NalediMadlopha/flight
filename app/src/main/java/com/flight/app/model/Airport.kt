package com.flight.app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Airport(
    val GMT: String,
    val codeIataAirport: String,
    val codeIataCity: String,
    val codeIcaoAirport: String,
    val codeIso2Country: String,
    val distance: String,
    val latitudeAirport: String,
    val longitudeAirport: String,
    val nameAirport: String,
    val nameCountry: String,
    val nameTranslations: String,
    val phone: String,
    val timezone: String
) : Parcelable