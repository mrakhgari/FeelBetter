package com.example.feelbetter

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button

/**
 * @mgh
 * sign up page
 *
 * TODO: adding logo and a placeholder to edittext
 */
class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
//        hideKeyboard(this)
        signUpClick()
    }

    //listener for clicking signup button that sends the user to login activity
    private fun signUpClick(){
        val button : Button = findViewById(R.id.signUp)
        button.setOnClickListener { // Perform action on click
            storeData()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    //gets data and stores it. If it was not successful it pops up a message and alerts the user
    private fun storeData():Boolean{

        return true
        //TODO @mgh
    }



//method for making the keypad disappeared after clicking outside the texts
     fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

    }
}