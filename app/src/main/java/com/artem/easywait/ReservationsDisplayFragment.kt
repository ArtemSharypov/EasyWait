package com.artem.easywait

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.dialog_new_reservation.view.*
import kotlinx.android.synthetic.main.fragment_reservations_display.*

/**
 * Created by Artem on 2017-12-21.
 */
class ReservationsDisplayFragment : Fragment() {
    private var fbAuth = FirebaseAuth.getInstance()
    private var reservations: MutableList<Reservation> = mutableListOf()
    private var adapter: ListViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_reservations_display, container, false)

        button_notify.setOnClickListener {
            notifyNextCustomer()
        }

        button_seat_next.setOnClickListener {
            seatNextCustomer()
        }

        button_new_reservation.setOnClickListener {
            createNewReservation()
        }

        adapter =  ListViewAdapter(context, reservations, fbAuth.currentUser!!.uid, tv_num_reservations)
        list_reservations.adapter = adapter

        return view
    }

    //Notifies the next customer via SMS (If they have a phone number added)
    private fun notifyNextCustomer() {
        val tempAdapter = adapter

        if(tempAdapter != null && tempAdapter.count > 0) {
            adapter?.notifyReservation(0)
        }
    }

    //Seats the next customer, by removing them from the list
    private fun seatNextCustomer() {
        val tempAdapter = adapter

        if(tempAdapter != null && tempAdapter.count > 0) {
            adapter?.deleteReservation(0)
        }
    }

    //Creates a dialog for input in order to create a new reservation, then passes it to the adapter
    private fun createNewReservation() {
        var promptsView = layoutInflater.inflate(R.layout.dialog_new_reservation, null)
        var alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setView(promptsView)

        var nameInputET = promptsView.input_name
        var numPeopleInputET = promptsView.input_num_people
        var phoneNum = promptsView.input_number

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Add Reservation", { dialogInterface, i ->
                    adapter?.createReservation(nameInputET.text.toString(), phoneNum.text.toString(), numPeopleInputET.text.toString().toInt())
                })
                .setNegativeButton("Cancel", { dialogInterface, i ->
                    dialogInterface.cancel()
                })

        var alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}