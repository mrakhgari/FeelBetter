package com.example.feelbetter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.feelbetter.R


/**
 * @mgh
 * This is the first page that the user sees. In this page the user can see the logo of the page and the options to sign up and to log in
 */
class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        signUpClick()
        loginClick()


    }
    //listener for clicking signup button that sends the user to signup activity
    private fun signUpClick(){
        val button : Button = findViewById(R.id.startSignUp)
        button.setOnClickListener { // Perform action on click
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }


    //listener for clicking signup button that sends the user to login activity
    private fun loginClick(){
        val button : TextView = findViewById(R.id.startLogin)
        button.setOnClickListener { // Perform action on click
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }



}