package com.flight.app.view.flightschedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flight.app.model.FlightSchedule
import com.flight.app.utils.Utils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.flight_schedule_list_item.*

class FlightScheduleAdapter(private val flightScheduleList: List<FlightSchedule>)
    : RecyclerView.Adapter<FlightScheduleAdapter.FlightScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.flight.app.R.layout.flight_schedule_list_item, parent, false)
        return FlightScheduleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return flightScheduleList.size
    }

    override fun onBindViewHolder(holder: FlightScheduleViewHolder, position: Int) {
        holder.bindData(flightScheduleList[position])
    }

    class FlightScheduleViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindData(flightSchedule: FlightSchedule) {
            flightScheduleAirlineNameTextView.text = flightSchedule.airline.name
            flightScheduleStatusTextView.text = flightSchedule.status

            flightScheduleStatusTextView.let {
                when (flightSchedule.status) {
                    "active", "scheduled" -> it.setCompoundDrawablesWithIntrinsicBounds(com.flight.app.R.drawable.green_dot, 0, 0, 0)
                    else -> it.setCompoundDrawablesWithIntrinsicBounds( com.flight.app.R.drawable.red_dot, 0, 0, 0)
                }
            }

            flightScheduleDepartureLabelValueWidget.setBottomText(Utils.formatDateTime(flightSchedule.departure.scheduledTime))
            flightScheduleFlightNumberLabelValueWidget.setBottomText(flightSchedule.flight.number)
            flightScheduleDestinationLabelValueWidget.setBottomText(flightSchedule.arrival.iataCode)
        }

    }

}