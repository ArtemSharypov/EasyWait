package com.artem.easywait

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signed_in.*

class SignedInActivity : AppCompatActivity() {

    private var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signed_in)

        setSupportActionBar(toolbar)

        fbAuth.addAuthStateListener {
            if(fbAuth.currentUser == null){
                this.finish()
            }
        }

        var reservationsDisplay = ReservationsDisplayFragment()

        var fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_frame_layout, reservationsDisplay, "reservationsDisplay")
        fragmentTransaction.commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_signedin, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var selected = super.onOptionsItemSelected(item)
        var id = item?.itemId

        when(id) {
            R.id.action_settings -> {
                selected = true
                switchToSettings()
            }

            R.id.action_sign_out-> {
                selected = true
                signOut()
            }
        }

        return selected
    }

    //Signs out the current user from Firebase
    private fun signOut(){
        fbAuth.signOut()
    }

    //Switches the fragment to the settings layout
    private fun switchToSettings(){
        var settingsFragment = SettingsFragment()

        var fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frame_layout, settingsFragment, "settingsFragment")
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START)
        } else if(supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            //todo: create confirmation if user wants to exit

            //Clear the activity stack, and then sets up for exiting the activity
            var intent = Intent(applicationContext, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("EXIT", true)
            startActivity(intent)
        }
    }
}
