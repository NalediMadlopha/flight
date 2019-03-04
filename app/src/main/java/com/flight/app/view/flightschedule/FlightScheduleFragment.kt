package com.flight.app.view.flightschedule


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.flight.app.R
import com.flight.app.model.City
import com.flight.app.model.FlightSchedule
import com.flight.app.viewmodel.FlightScheduleViewModel
import kotlinx.android.synthetic.main.flight_schedule_fragment.*


class FlightScheduleFragment : Fragment(), FlightScheduleView {

    private lateinit var rootView: View
    private lateinit var viewModel: FlightScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.flight_schedule_fragment, container, false)
        setupToolBar()

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this.activity!!).get(FlightScheduleViewModel::class.java)
        viewModel.init(this)

        viewModel.fetchCity()
    }

    override fun displayError() {
        flightScheduleDetailsProgressBar.visibility = View.GONE
        flightScheduleDetailsContainer.visibility = View.GONE
        flightScheduleErrorTextView.visibility = View.VISIBLE
    }

    override fun displayEmptyData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun displayFlightSchedule(flightScheduleList: List<FlightSchedule>) {
        flightScheduleDetailsProgressBar.visibility = View.GONE
        flightScheduleErrorTextView.visibility = View.GONE

        flightScheduleRecylcerView.adapter = FlightScheduleAdapter(flightScheduleList)

        flightScheduleDetailsContainer.visibility = View.VISIBLE
    }

    override fun displayCityDetails(city: City) {
        flightScheduleAirportNameTextView.text = viewModel.getAirportName()
        flightScheduleAirportLocation.text = city.nameCity

        viewModel.fetchAirportSchedule()
    }

    private fun setupToolBar() {
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        }
    }

}