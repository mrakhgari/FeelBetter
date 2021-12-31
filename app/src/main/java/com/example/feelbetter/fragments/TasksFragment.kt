package com.example.feelbetter.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.feelbetter.R
import com.example.feelbetter.activities.CreateTaskActivity
import com.example.feelbetter.activities.DoneTasksActivity
import com.example.feelbetter.adapters.TasksItemAdapter
import com.example.feelbetter.firestore.FirestoreClass
import com.example.feelbetter.models.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : Fragment() {
    var taskList: ArrayList<Task> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        var fab: FloatingActionButton = view.findViewById(R.id.fab_create_task)
        fab.setOnClickListener {
            startActivity(Intent(this.activity, CreateTaskActivity::class.java))
        }

        var doneTasks = view.findViewById<Button>(R.id.btn_done_tasks)
        doneTasks.setOnClickListener {
            startActivity(Intent(this.activity, DoneTasksActivity::class.java))
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        FirestoreClass().getOnCompleteTasks(this)

    }

    fun onCompletedTaskToUI(taskList: ArrayList<Task>) {
        if (taskList.size > 0) {
            this.taskList = taskList
            rv_tasks_list.visibility = View.VISIBLE
            tv_no_task_available.visibility = View.GONE

            rv_tasks_list.layoutManager = LinearLayoutManager(this.context)
            rv_tasks_list.setHasFixedSize(true)
            val adapter = this.context?.let { TasksItemAdapter(it, taskList) }
            rv_tasks_list.adapter = adapter
            adapter!!.setOnDeleteListener(object : TasksItemAdapter.OnDeleteListener {
                override fun onClick(position: Int, model: Task) {
                    model.documentId?.let { FirestoreClass().deleteTask(this@TasksFragment, it) }
                }
            })

            adapter.setOnEditListener(object : TasksItemAdapter.OnEditListener {
                override fun onClick(position: Int, model: Task) {

//                    manageEditDialog(this@TasksFragment, position)
                    model.documentId?.let {
                        FirestoreClass().editTask(
                            this@TasksFragment,
                            it,
                            "edited"
                        )
                    }

                }
            })

            adapter.setOnDoneListener(object : TasksItemAdapter.OnDoneListener {
                override fun onClick(position: Int, model: Task) {
                    model.documentId?.let { FirestoreClass().doneTask(this@TasksFragment, it) }

                }
            })

        } else {
            rv_tasks_list.visibility = View.GONE
            tv_no_task_available.visibility = View.VISIBLE
        }
    }

    fun manageEditDialog(model: Task) {
    }

}