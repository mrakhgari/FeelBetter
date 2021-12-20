package com.example.feelbetter.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.feelbetter.R
import com.example.feelbetter.firestore.FirestoreClass
import com.example.feelbetter.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*


/**
 * @mgh
 * sign up page
 *
 * TODO: adding logo and a placeholder to edittext
 */
class SignupActivity : BaseActivity() {
    var datePickerDialog: DatePickerDialog? = null
    lateinit var dateButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
            )
        }
        setPaddingForFields()
        initDatePicker()
        dateButton = findViewById(R.id.signUpDate)
        dateButton.text = todayDate
        dateButton.setOnClickListener(View.OnClickListener { datePickerDialog!!.show() })

        signUpClick()
    }

    private fun setPaddingForFields(){
        val scale = resources.displayMetrics.density
        val padding_5dp = (5 * scale + 0.5f).toInt()
        val padding_40dp = (40 * scale + 0.5f).toInt()
        val email: EditText = findViewById(R.id.signUpEmail)
        email.setPadding(padding_40dp, padding_5dp, padding_5dp, padding_5dp)
        val pass: EditText = findViewById(R.id.signUpPassword)
        pass.setPadding(padding_40dp, padding_5dp, padding_5dp, padding_5dp)
        val user: EditText = findViewById(R.id.signUpUsername)
        user.setPadding(padding_40dp, padding_5dp, padding_5dp, padding_5dp)
    }



    //listener for clicking signup button that sends the user to login activity
    private fun signUpClick() {
        val button: Button = findViewById(R.id.signUp)
        button.setOnClickListener { // Perform action on click
            if (validationDetails())
               storeData()
        }
    }

    //gets data and stores it. If it was not successful it pops up a message and alerts the user
    private fun storeData(): Boolean {
        val email: String = findViewById<TextView>(R.id.signUpEmail).text.toString()
        val password: String = findViewById<TextView>(R.id.signUpPassword).text.toString()
        showProgressDialog(resources.getString(R.string.please_wait))
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->
                    hideProgressDialog()
                    // if the registration is successfully done
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        showMessageSnackBar("You are registered successfully")
                        val user = User(
                            firebaseUser.uid,
                            findViewById<TextView>(R.id.signUpUsername).text.toString(),
                            findViewById<TextView>(R.id.signUpUsername).text.toString(),
                            findViewById<Button>(R.id.signUpDate).text.toString(),
                            email
                        )

                        FirestoreClass().registerUser(this, user)
                        userRegistrationSuccess()
                    } else {
                        showMessageSnackBar(task.exception!!.message.toString(), true)
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

    fun userRegistrationSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }


    private fun validationDetails():Boolean{
        return when {
            TextUtils.isEmpty(signUpUsername.text.toString().trim{ it <= ' '}) -> {
                showMessageSnackBar(resources.getString(R.string.err_msg_enter_first_name) , true)
                false
            }

            TextUtils.isEmpty(signUpEmail.text.toString().trim{ it <= ' '}) -> {
                showMessageSnackBar(resources.getString(R.string.err_msg_enter_new_email) , true)
                false
            }

            TextUtils.isEmpty(signUpPassword.text.toString().trim{ it <= ' '}) -> {
                showMessageSnackBar(resources.getString(R.string.err_msg_enter_new_password) , true)
                false
            }


            else -> true

        }
    }
    private val todayDate: String
        get() {
            val cal = Calendar.getInstance()
            val year = cal[Calendar.YEAR]
            var month = cal[Calendar.MONTH]
            month += 1
            val day = cal[Calendar.DAY_OF_MONTH]
            return makeDateString(day, month, year)
        }

    private fun initDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            var month = month
            month += 1
            val date = makeDateString(day, month, year)
            dateButton!!.text = date
        }
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]
        val style = AlertDialog.THEME_HOLO_LIGHT
        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        return getMonthFormat(month) + " " + day + " " + year
    }

    private fun getMonthFormat(month: Int): String {
        if (month == 1) return "JAN"
        if (month == 2) return "FEB"
        if (month == 3) return "MAR"
        if (month == 4) return "APR"
        if (month == 5) return "MAY"
        if (month == 6) return "JUN"
        if (month == 7) return "JUL"
        if (month == 8) return "AUG"
        if (month == 9) return "SEP"
        if (month == 10) return "OCT"
        if (month == 11) return "NOV"
        return if (month == 12) "DEC" else ""
    }


}