package com.artem.easywait

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.listview_row_reservation.view.*

/**
 * Created by Artem on 2017-12-19.
 */
class ListViewAdapter : BaseAdapter {

    private var inflater: LayoutInflater
    private var reservations: MutableList<Reservation>

    constructor(context: Context, reservations: MutableList<Reservation>) {
        this.inflater = LayoutInflater.from(context)
        this.reservations = reservations
    }

    override fun getCount(): Int {
        return reservations.size
    }

    override fun getItem(position: Int): Any {
        return reservations[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup?): View {
        var view: View = convertView

        if(view == null) {
            view = inflater.inflate(R.layout.listview_row_reservation, parent, false)
        }

        view.tv_pos.text = position.toString()
        /* needs to set:
           view.tv_name
           view.tv_num_people
           view.tv_wait_time
         */

        /* then buttons for
            view.button_finished
            view.button_edit
            view.button_notify
         */

        return view
    }

    fun notifyReservation(){

    }

    fun editReservation(){

    }

    fun deleteReservation(){

    }
}