package com.artem.easywait

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.dialog_new_reservation.view.*
import kotlinx.android.synthetic.main.listview_row_reservation.view.*
import java.util.*

/**
 * Created by Artem on 2017-12-19.
 */
class ListViewAdapter : BaseAdapter {

    private var inflater: LayoutInflater
    private var reservations: MutableList<Reservation>
    private var database = FirebaseDatabase.getInstance().reference
    private var userID: String
    private var tvNumReservations: TextView
    private var context: Context

    constructor(context: Context, reservations: MutableList<Reservation>, userToken: String, tvNumReservations: TextView) {
        this.inflater = LayoutInflater.from(context)
        this.reservations = reservations
        this.userID = userToken
        this.tvNumReservations = tvNumReservations
        this.context = context

        updateReservations()
    }

    fun updateReservations() {
        val reservationsListener = object : ValueEventListener{
            override fun onDataChange(dataSnapShot: DataSnapshot) {
                reservations.clear()

                dataSnapShot.children.mapNotNullTo(reservations) {
                    it.getValue<Reservation>(Reservation::class.java)
                }

                tvNumReservations.text = reservations.size.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }

        database.child("users").child(userID).addValueEventListener(reservationsListener)
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

        var currReserv: Reservation = reservations.get(position)

        var calendar = GregorianCalendar()
        var currHour: Int = calendar.get(Calendar.HOUR)
        var currMinute: Int = calendar.get(Calendar.MINUTE)
        var timeInMinutes: Int = currHour * 60 + currMinute

        var waitTime: Int = timeInMinutes - currReserv.arrivalTimeMinutes - currReserv.arrivalTimeHours*60

        view.tv_pos.text = position.toString()
        view.tv_name.text = currReserv.name
        view.tv_wait_time.text = waitTime.toString()
        view.tv_num_people.text = currReserv.numPeople.toString()

        view.button_edit.setOnClickListener {
            editReservation(position)
        }

        view.button_finished.setOnClickListener {
            deleteReservation(position)
        }

        view.button_notify.setOnClickListener {
            notifyReservation(position)
        }

        return view
    }

    //Notifies the next customer via SMS (If they have a phone number added)
    fun notifyReservation(pos: Int){
        var reservation: Reservation = reservations[pos]

        //todo grab info from reservation, and send the api request
    }

    //Dialog popup to allow the ability to edit the specified reservation
    //If it gets saved, it'll update reservations as well as the database
    fun editReservation(pos: Int){
        var reservation: Reservation = reservations[pos]
        var promptsView = inflater.inflate(R.layout.dialog_edit_reservation, null)
        var alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setView(promptsView)

        var nameInputET = promptsView.input_name
        var numPeopleInputET = promptsView.input_num_people
        var phoneNum = promptsView.input_number

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Save Reservation", { dialogInterface, i ->
                    reservation.name = nameInputET.text.toString()
                    reservation.number = phoneNum.text.toString()
                    reservation.numPeople = numPeopleInputET.text.toString().toInt()

                    notifyDataSetChanged()

                    updateDB()
                })
                .setNegativeButton("Cancel", { dialogInterface, i ->
                    dialogInterface.cancel()
                })

        var alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    //Deletes the specified reservation
    fun deleteReservation(pos: Int){
        if(pos < reservations.size){
            reservations.removeAt(pos)

            tvNumReservations.text = reservations.size.toString()

            notifyDataSetChanged()

            updateDB()
        }
    }

    fun createReservation(name: String, number: String, numPeople: Int){
        var reservation = Reservation(name, number, numPeople)
        reservations.add(reservation)

        tvNumReservations.text = reservations.size.toString()

        notifyDataSetChanged()

        updateDB()
    }

    //Updates the list of reservations in the database
    private fun updateDB() {
        database.child("users").child(userID).child("reservations").setValue(reservations)
    }

}