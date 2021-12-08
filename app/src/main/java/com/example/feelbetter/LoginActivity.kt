package com.example.feelbetter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.app.Activity
import android.view.inputmethod.InputMethodManager


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        hideSoftKeyboard(this)
        getDate()
    }
    fun getDate(){
        val editText :EditText = findViewById(R.id.loginEmail)
        var email = editText.text
        val editText2 :EditText = findViewById(R.id.loginPassword)
        var password = editText2.text
        println(email)
        println(password)
    }


    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }

}