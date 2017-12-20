package com.artem.easywait

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login.setOnClickListener {
           login()
        }

        link_signup.setOnClickListener {
           switchToSignUp()
        }
    }

    //Tries to use the email and password provided to sign into firebase
    fun login(){
        Snackbar.make(button_login, "Authenticating...", Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null).show()

        fbAuth.signInWithEmailAndPassword(input_email.text.toString(), input_password.text.toString())
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        var intent = Intent(this, SignedInActivity::class.java)
                        intent.putExtra("id", fbAuth.currentUser?.email)
                        startActivity(intent)

                    } else {
                        Snackbar.make(button_login, "Error: ${task.exception?.message}", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Action", null).show()
                    }
                }
    }

    //Switches to the SignUpActivity
    fun switchToSignUp(){
        var intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}
