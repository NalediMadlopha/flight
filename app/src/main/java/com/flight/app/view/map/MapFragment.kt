package com.flight.app.view.map


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.flight.app.model.Airport
import com.flight.app.view.flightschedule.FlightScheduleActivity
import com.flight.app.viewmodel.MapFragmentViewModel
import com.flight.app.viewmodel.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), OnMapReadyCallback, LocationListener, MapFragmentView, GoogleMap.OnMarkerClickListener {

    private lateinit var viewModel: MapFragmentViewModel
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.flight.app.R.layout.maps_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (childFragmentManager.findFragmentById(com.flight.app.R.id.map) as? SupportMapFragment)?.getMapAsync(this)

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory { MapFragmentViewModel(this, activity?.application!!) }
        ).get(MapFragmentViewModel::class.java)
    }

    // NB: At this point on the app the location permission has been granted
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        this.googleMap.isMyLocationEnabled = true
        googleMap.setOnMarkerClickListener(this)
    }

    // NB: At this point on the app the location permission has been granted
    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()

        val application = activity?.application!!
        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5L, 20F, this)
    }

    override fun onLocationChanged(location: Location) {
        googleMap.clear()

        val latitude = location.latitude
        val longitude = location.longitude
        val currentLatLng = LatLng(latitude, longitude)

        val circleOptions = CircleOptions()
            .center(currentLatLng)
            .radius(DISTANCE)
            .strokeWidth(0F)
            .fillColor(0x220000FF)

        googleMap.addCircle(circleOptions)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 11f))
        viewModel.fetchNearbyAirports(latitude.toString(), longitude.toString(), DISTANCE.toString())
    }

    override fun displayAirportMapMarkers(airportList: List<Airport>) {
        for (airport in airportList) {
            val airportLocation = LatLng(airport.latitudeAirport.toDouble(), airport.longitudeAirport.toDouble())

            if (airport.distance.toDouble() < 5.0) {
                this.googleMap.addMarker(MarkerOptions()
                    .position(airportLocation)
                    .title(airport.nameAirport)
                    .snippet("${airport.codeIataCity}|${airport.codeIataAirport}")
                )
            }
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val intent = Intent(context, FlightScheduleActivity::class.java)
        intent.putExtra(SELECTED_AIRPORT_NAME, marker.title)
        intent.putExtra(SELECTED_IATA_CODE, marker.snippet)

        startActivity(intent)

        return false
    }

    override fun displayError() {
        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
    }

    override fun displayNoAirportNearby() {
        Toast.makeText(context, "No airports", Toast.LENGTH_LONG).show()
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        /* no-op */
    }

    override fun onProviderEnabled(provider: String?) {
        /* no-op */
    }

    override fun onProviderDisabled(provider: String?) {
        /* no-op */
    }

    companion object {
        private const val DISTANCE = 5000.0
        private const val SELECTED_AIRPORT_NAME = "SELECTED_AIRPORT_NAME"
        private const val SELECTED_IATA_CODE = "SELECTED_IATA_CODE"
    }

}
