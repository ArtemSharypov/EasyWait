package com.artem.easywait

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        button_create_account.setOnClickListener {
            signUp()
        }

        link_login.setOnClickListener {
            switchToLogin()
        }
    }

    //Attempts to create a new user from the email and password entered
    fun signUp() {
        fbAuth.createUserWithEmailAndPassword(input_email.text.toString(), input_password.text.toString())
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        var intent = Intent(this, SignedInActivity::class.java)
                        intent.putExtra("id", fbAuth.currentUser?.email)
                        startActivity(intent)
                    } else {
                        Snackbar.make(button_create_account, "Error: ${task.exception?.message}", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Action", null).show()
                    }
                }
    }

    //Returns back to the LoginActivity
    fun switchToLogin() {
        finish()
    }
}
