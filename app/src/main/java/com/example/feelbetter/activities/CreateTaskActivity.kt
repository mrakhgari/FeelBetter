package com.example.feelbetter.activities

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.widget.Toast
import com.example.feelbetter.R
import com.example.feelbetter.firestore.FirestoreClass
import com.example.feelbetter.models.Task
import kotlinx.android.synthetic.main.activity_create_task.*
import java.util.*

class CreateTaskActivity : BaseActivity() {
    var startHour = -1
    var startMinute = -1
    var endHour = -1
    var endMinute = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        setupActionBar()
        btn_start_time.setOnClickListener {
            popTimePicker(true)
        }

        btn_due_time.setOnClickListener {
            popTimePicker(false)
        }

        btn_create.setOnClickListener {
            when {
                startHour == -1 -> {
                    Toast.makeText(this, "Select a start time", Toast.LENGTH_LONG).show()
                }
                endHour == -1 -> {
                    Toast.makeText(this, "Select an end time", Toast.LENGTH_LONG).show()
                }
                et_task_description.text!!.isEmpty() -> {
                    Toast.makeText(this, "There is no task to be added", Toast.LENGTH_LONG)
                        .show()
                }
                else -> {
                    val task = Task(
                        String.format(Locale.getDefault(), "%02d:%02d", startHour, startMinute),
                        String.format(
                            Locale.getDefault(), "%02d:%02d", endHour, endMinute
                        ),
                        et_task_description.text!!.toString(),
                        FirestoreClass().getCurrentUserId(),
                        false
                    )
                    showProgressDialog(resources.getString(R.string.please_wait))
                    FirestoreClass().createTask(this, task)
                }
            }
        }
    }

    fun popTimePicker(isStart: Boolean) {
        val onTimeSetListener =
            OnTimeSetListener { view, hourOfDay, minute ->
                if (isStart) {
                    startHour = hourOfDay
                    startMinute = minute
                    btn_start_time.text = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        startHour,
                        startMinute
                    )
                } else {
                    endHour = hourOfDay
                    endMinute = minute
                    btn_due_time.text = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        endHour,
                        endMinute
                    )
                }
            }
        val style = AlertDialog.THEME_HOLO_DARK
        val timePickerDialog: TimePickerDialog = if (isStart) {
            TimePickerDialog(this, style, onTimeSetListener, startHour, startMinute, true)
        } else TimePickerDialog(this, style, onTimeSetListener, endHour, endMinute, true)
        timePickerDialog.setTitle("select time")
        timePickerDialog.show()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_create_task_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = resources.getString(R.string.create_task_title)
        }
        toolbar_create_task_activity.setNavigationOnClickListener { onBackPressed() }
    }

    fun createTaskSuccess() {
        hideProgressDialog()
        finish()
    }
}