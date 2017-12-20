package com.artem.easywait

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signed_in.*

class SignedInActivity : AppCompatActivity() {

    private var fbAuth = FirebaseAuth.getInstance()
    private var reservations: MutableList<Reservation> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signed_in)


        //will need a button that has onclick signout, just calls signOut

        fbAuth.addAuthStateListener {
            if(fbAuth.currentUser == null){
                this.finish()
            }
        }

        button_notify.setOnClickListener {
            notifyNextCustomer()
        }

        button_seat_next.setOnClickListener {
            seatNextCustomer()
        }

        button_new_reservation.setOnClickListener {
            createNewReservation()
        }

        list_reservations.adapter = ListViewAdapter(this, reservations)
    }

    //Signs out the current user from Firebase
    fun signOut(){
        fbAuth.signOut()
    }

    //Notifies the next customer via SMS (If they have a phone number added)
    fun notifyNextCustomer() {
        //needs to check if there a phone number for the customer
        //if so, then notifies them
    }

    //Seats the next customer, by removing them from the list
    fun seatNextCustomer() {
        //just needs to remove the first entry in the list, and update the db
    }

    //Adds a new reservation to the list
    fun createNewReservation() {
        //Needs the dialog_new_reservation layout to pop up, then grab information from it
        //then update the list & the db
    }
}
