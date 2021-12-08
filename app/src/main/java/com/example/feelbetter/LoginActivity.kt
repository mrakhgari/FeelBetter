package com.example.feelbetter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.content.Intent
import android.provider.AlarmClock.EXTRA_MESSAGE
/**
 * @mgh
 * login page
 *
 * TODO: adding logo and a placeholder to edittext
 */

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        hideSoftKeyboard(this)
        loginClick()
    }



    //listener for clicking signup button that sends the user to login activity
    private fun loginClick(){
        val button : Button = findViewById(R.id.login)
        button.setOnClickListener { // Perform action on click
            getDate()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    //gets the input data from edit texts
    private fun getDate(): Boolean{
        val editText :EditText = findViewById(R.id.loginEmail)
        val email = editText.text
        val editText2 :EditText = findViewById(R.id.loginPassword)
        val password = editText2.text
        return validateInputData(email.toString() , password.toString())

    }

    //checks whether the user has registered before
    private fun validateInputData(email :String, password:String):Boolean{

        if (email.toString() == "admin" && password.toString() == "admin") {
            return true
        }
        return false
    }

    //method for making the keypad disappeared after clicking outside the texts
    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

    }



}