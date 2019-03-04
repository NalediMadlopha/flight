package com.flight.app.viewmodel.async

import android.os.AsyncTask
import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import com.flight.app.model.City
import com.flight.app.repo.AirportRepository
import com.flight.app.view.flightschedule.FlightScheduleView
import retrofit2.Response

class FetchCityTask(
    private val view: FlightScheduleView,
    @VisibleForTesting val repository: AirportRepository
) : AsyncTask<String, Void, Response<List<City>>>() {

    constructor(view: FlightScheduleView): this(view, AirportRepository())

    override fun doInBackground(@NonNull vararg params: String): Response<List<City>> {
        return repository.getCity(params[0])
    }

    override fun onPostExecute(response: Response<List<City>>) {
        super.onPostExecute(response)
        val data = response.body()

        if (response.isSuccessful && !data.isNullOrEmpty()) {
            view.displayCityDetails(data[0])
        } else {
            view.displayError()
        }
    }

}
