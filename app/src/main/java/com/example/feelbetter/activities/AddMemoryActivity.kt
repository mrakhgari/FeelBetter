package com.example.feelbetter.activities

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.feelbetter.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_memory.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class AddMemoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memory)

        initViews()
    }

    private fun initViews() {
        add_memory_submit.setOnClickListener {
            if (isInputsAvailable()) {
                hideKeyboard()
                add_memory_progressbar.show()
                add_memory_submit.gone()
                addMemory()
            }
        }
    }

    private fun isInputsAvailable(): Boolean {
        val result: Boolean

        if (add_memory_title.text?.length == 0) {
            result = false

            add_memory_root.snack(
                "Every Memory should have a Title!"
            )
        } else {
            result = true
        }

        return result
    }

    private fun addMemory() {
        val ref = Firebase.database.getReference("memory")
        val currentUser = FirebaseAuth.getInstance().currentUser

        val dateFormatter = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
        val timeFormatter = SimpleDateFormat("KK:mm aaa", Locale.getDefault())

        val date =
            "${dateFormatter.format(System.currentTimeMillis())} at ${timeFormatter.format(System.currentTimeMillis())}"

        val data = mapOf(
            "title" to add_memory_title.text?.toString()?.trim(),
            "date" to date,
            "description" to add_memory_des.text?.toString()?.trim()
        )

        ref.child(currentUser?.uid ?: "")
            .push()
            .setValue(data)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    finish()
                } else if (it.isCanceled) {
                    add_memory_root.snack(
                        "Some Error Happened, Try Again"
                    )
                    add_memory_progressbar.invisible()
                    add_memory_submit.show()
                }
            }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if no view has focus
        val currentFocusedView = this.currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    private fun View.snack(
        msg: CharSequence,
        duration: Int = Snackbar.LENGTH_SHORT
    ) = Snackbar.make(this, msg, duration).also { it.show() }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

}

fun Any?.log(msg: Any? = "") {
    Log.d("bandOfBrothers", "$this -> $msg")
}