package com.flight.app.viewmodel.async

import android.os.AsyncTask
import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import com.flight.app.model.Airport
import com.flight.app.repo.AirportRepository
import com.flight.app.view.map.MapFragmentView
import retrofit2.Response

// TODO: Use Coroutines/RxJava instead of AsyncTask
// Using AsyncTask for brevity, currently having issues testing Corountines/RxJava
open class FetchNearbyAirportsTask(
    private val view: MapFragmentView,
    @VisibleForTesting val repository: AirportRepository
) : AsyncTask<String, Void, Response<List<Airport>>>() {

    constructor(view: MapFragmentView): this(view, AirportRepository())

    override fun doInBackground(@NonNull vararg params: String): Response<List<Airport>> {
        return repository.getNearbyAirports(params[0], params[1], params[2])
    }

    override fun onPostExecute(response: Response<List<Airport>>) {
        super.onPostExecute(response)
        val data = response.body()

        if (response.isSuccessful) {
            if (!data.isNullOrEmpty()) {
                view.displayAirportMapMarkers(data)
            } else if (data != null && data.isEmpty()) {
                view.displayNoAirportNearby()
            } else {
                view.displayError()
            }
        } else {
            view.displayError()
        }
    }

}