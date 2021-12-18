package com.example.feelbetter.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.example.feelbetter.R
import com.example.feelbetter.firestore.FirestoreClass
import com.example.feelbetter.models.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * @mgh
 * login page
 *
 * TODO: adding logo and a placeholder to edittext
 */

class LoginActivity : BaseActivity() {
    var datePickerDialog: DatePickerDialog? = null
    lateinit var dateButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
            )
        }

        val scale = resources.displayMetrics.density
        val padding_5dp = (5 * scale + 0.5f).toInt()
        val padding_40dp = (40 * scale + 0.5f).toInt()
        val email: EditText = findViewById(R.id.loginEmail)
        email.setPadding(padding_40dp, padding_5dp, padding_5dp, padding_5dp)
        email.setHint("Enter your email");
        val pass: EditText = findViewById(R.id.loginPassword)
        pass.setPadding(padding_40dp, padding_5dp, padding_5dp, padding_5dp)

//        hideSoftKeyboard(this)
        loginClick()
        val forgotPassword: TextView = findViewById(R.id.forget_password)
        forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }


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
        showProgressDialog(resources.getString(R.string.please_wait))
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                hideProgressDialog()
                if (task.isSuccessful) {
                    FirestoreClass().getUserDetails(this)
                } else {
                    showMessageSnackBar(task.exception!!.message.toString(), true)
                }
            }
        return false
    }

    //method for making the keypad disappeared after clicking outside the texts
    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

    }

    fun userLoggedInSuccess(user: User) {
        Log.i("First Name", user.firstNAme)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }





}