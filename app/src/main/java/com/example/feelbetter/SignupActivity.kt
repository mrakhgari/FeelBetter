package com.example.feelbetter

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth


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
    private fun signUpClick() {
        val button: Button = findViewById(R.id.signUp)
        button.setOnClickListener { // Perform action on click
            storeData()
        }
    }

    //gets data and stores it. If it was not successful it pops up a message and alerts the user
    private fun storeData(): Boolean {
        val email: String = findViewById<TextView>(R.id.signUpEmail).text.toString()
        val password: String = findViewById<TextView>(R.id.signUpPassword).text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->
                    // if the registration is successfully done
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        Toast.makeText(this, "You are registered successfully.", Toast.LENGTH_SHORT)
                            .show()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id", firebaseUser.uid)
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
            )
        return true
        //TODO @mgh
    }


    //method for making the keypad disappeared after clicking outside the texts
    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

    }
}