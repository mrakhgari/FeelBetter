package com.example.feelbetter.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import com.example.feelbetter.R
import com.google.firebase.auth.FirebaseAuth


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
        val scale = resources.displayMetrics.density
        val padding_5dp = (5 * scale + 0.5f).toInt()
        val padding_40dp = (40 * scale + 0.5f).toInt()
        val email :EditText = findViewById(R.id.loginEmail)
        email.setPadding(padding_40dp,padding_5dp,padding_5dp,padding_5dp)
        val pass :EditText = findViewById(R.id.loginPassword)
        pass.setPadding(padding_40dp,padding_5dp,padding_5dp,padding_5dp)

//        hideSoftKeyboard(this)
        loginClick()
    }


    //listener for clicking signup button that sends the user to login activity
    private fun loginClick() {
        val button: Button = findViewById(R.id.login)
        button.setOnClickListener { // Perform action on click
            getDate()
        }
    }


    //gets the input data from edit texts
    private fun getDate(): Boolean {
        val editText: EditText = findViewById(R.id.loginEmail)
        val email = editText.text
        val editText2: EditText = findViewById(R.id.loginPassword)
        val password = editText2.text
        return validateInputData(email.toString(), password.toString())

    }

    //checks whether the user has registered before
    private fun validateInputData(email: String, password: String): Boolean {
//
//        if (email.toString() == "admin" && password.toString() == "admin") {
//            return true
//        }
//        return false
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "You are logged in successfully.", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                    intent.putExtra("email", email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        return false
    }

    //method for making the keypad disappeared after clicking outside the texts
    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

    }


}