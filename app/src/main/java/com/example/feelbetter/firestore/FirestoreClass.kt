package com.example.feelbetter.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.feelbetter.MyProfileActivity
import com.example.feelbetter.activities.LoginActivity
import com.example.feelbetter.activities.MainActivity
import com.example.feelbetter.activities.SignupActivity
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

    private fun getCurrentUserId(): String {
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
}