package com.example.feelbetter.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feelbetter.R
import com.example.feelbetter.adapters.DoneTasksItemAdapter
import com.example.feelbetter.adapters.TasksItemAdapter
import com.example.feelbetter.firestore.FirestoreClass
import com.example.feelbetter.models.Task
import kotlinx.android.synthetic.main.activity_done_tasks.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.fragment_tasks.*
import java.util.ArrayList

class DoneTasksActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_done_tasks)
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getCompletedTasks(this)
        setupActionBar()
    }

    fun showTaskOnUI(taskList: ArrayList<Task>) {
        val adapter = DoneTasksItemAdapter(this, taskList)
        rv_done_tasks_list.layoutManager = LinearLayoutManager(this)
        rv_done_tasks_list.setHasFixedSize(true)
        rv_done_tasks_list.adapter = adapter
        hideProgressDialog()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_done_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = resources.getString(R.string.done_tasks)
        }
        toolbar_done_activity.setNavigationOnClickListener { onBackPressed() }
    }

}