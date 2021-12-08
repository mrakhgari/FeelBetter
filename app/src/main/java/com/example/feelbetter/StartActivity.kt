package com.example.feelbetter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


/**
 * @mgh
 * This is the first page that the user sees. In this page the user can see the logo of the page and the options to sign up and to log in
 */
class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }
}