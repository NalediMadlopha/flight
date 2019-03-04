package com.flight.app.viewmodel.async

import android.os.AsyncTask
import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import com.flight.app.model.FlightSchedule
import com.flight.app.repo.AirportRepository
import com.flight.app.view.flightschedule.FlightScheduleView
import retrofit2.Response

class FetchAirportScheduleTask(
    private val view: FlightScheduleView,
    @VisibleForTesting val repository: AirportRepository
) : AsyncTask<String, Void, Response<List<FlightSchedule>>>() {

    constructor(view: FlightScheduleView): this(view, AirportRepository())

    override fun doInBackground(@NonNull vararg params: String): Response<List<FlightSchedule>> {
        return repository.getAirportSchedule(params[0], params[1])
    }

    override fun onPostExecute(response: Response<List<FlightSchedule>>) {
        super.onPostExecute(response)
        val data = response.body()

        if (response.isSuccessful && !data.isNullOrEmpty()) {
            view.displayFlightSchedule(data)
        } else {
            view.displayError()
        }
    }

}
