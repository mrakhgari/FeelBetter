package com.example.feelbetter.firestore

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.feelbetter.MyProfileActivity
import com.example.feelbetter.activities.*
import com.example.feelbetter.fragments.TasksFragment
import com.example.feelbetter.models.Task
import com.example.feelbetter.models.User
import com.example.feelbetter.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignupActivity, userInfo: User) {
        mFirestore.collection(Constants.USERS).document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }.addOnFailureListener { e -> // todo
                Log.e(activity.javaClass.simpleName, "Error in registering user", e)
            }
    }

    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    fun updateUserProfileData(activity: MyProfileActivity, userHashMap: HashMap<String, Any>) {
        mFirestore.collection(Constants.USERS).document(getCurrentUserId()).update(userHashMap)
            .addOnSuccessListener {
                activity.profileUpdateSuccess()
            }.addOnFailureListener { e ->
                activity.hideProgressDialog()
                Toast.makeText(activity, "Error on update profile", Toast.LENGTH_LONG).show()
            }
    }

    fun getUserDetails(activity: Activity) {
        mFirestore.collection(Constants.USERS).document(getCurrentUserId()).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)!!
                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                    is MainActivity -> {
                        activity.updateNavigationUserDetails(user)
                    }
                    is MyProfileActivity -> {
                        activity.setUserDataUI(user)
                    }
                }
            }
    }


    fun createTask(activity: CreateTaskActivity, task: Task) {
        mFirestore.collection(Constants.TASKS).document().set(task, SetOptions.merge())
            .addOnSuccessListener {
                Toast.makeText(activity, "Task created successfully", Toast.LENGTH_LONG)
                    .show()
                activity.createTaskSuccess()

            }.addOnFailureListener { e ->
                Toast.makeText(activity, "Take created failed", Toast.LENGTH_LONG).show()
            }
    }

    fun getOnCompleteTasks(fragment: TasksFragment) {
        Log.i("TASK", "IN GET TASKS")
        mFirestore.collection(Constants.TASKS).whereEqualTo(Constants.OWNER, getCurrentUserId())
            .whereEqualTo(Constants.FINISHED, false).get().addOnSuccessListener { doc ->
                Log.i("TASKS", doc.documents.toString())
                val taskList: ArrayList<Task> = ArrayList()
                for (i in doc.documents) {
                    val task = i.toObject(Task::class.java)!!
                    task.documentId = i.id
                    taskList.add(task)
                }
                fragment.onCompletedTaskToUI(taskList)
            }.addOnFailureListener {
                Log.e("TASKS", "Error while creating tasks")
            }
    }

    fun getCompletedTasks(activity: DoneTasksActivity) {
        mFirestore.collection(Constants.TASKS).whereEqualTo(Constants.OWNER, getCurrentUserId())
            .whereEqualTo(Constants.FINISHED, true).get().addOnSuccessListener { doc ->
                Log.i("TASKS", doc.documents.toString())
                val taskList: ArrayList<Task> = ArrayList()
                for (i in doc.documents) {
                    val task = i.toObject(Task::class.java)!!
                    task.documentId = i.id
                    taskList.add(task)
                }
                activity.showTaskOnUI(taskList)
            }.addOnFailureListener {
                Log.e("TASKS", "Error while creating tasks")
            }
    }

    fun doneTask(fragment: TasksFragment, documentId: String) {
        mFirestore.collection(Constants.TASKS).document(documentId).update(Constants.FINISHED, true)
            .addOnSuccessListener {
                getOnCompleteTasks(fragment)
            }
    }

    fun editTask(fragment: TasksFragment, documentId: String, task: String) {
        mFirestore.collection(Constants.TASKS).document(documentId).update(Constants.TASK, task)
            .addOnSuccessListener {
                getOnCompleteTasks(fragment)
            }
    }

    fun deleteTask(fragment: TasksFragment, documentId: String) {
        mFirestore.collection(Constants.TASKS).document(documentId).delete().addOnSuccessListener {
            getOnCompleteTasks(fragment)
        }
    }
}