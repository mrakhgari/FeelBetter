package com.example.feelbetter.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
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

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
            )
        }
        signUpClick()
        loginClick()


    }

    //listener for clicking signup button that sends the user to signup activity
    private fun signUpClick() {
        val button: Button = findViewById(R.id.startSignUp)
        button.setOnClickListener { // Perform action on click
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }


    //listener for clicking signup button that sends the user to login activity
    private fun loginClick() {
        val button: TextView = findViewById(R.id.startLogin)
        button.setOnClickListener { // Perform action on click
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


}