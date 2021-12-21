package com.example.feelbetter.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import com.example.feelbetter.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
            )
        }

        val currentUser = FirebaseAuth.getInstance().currentUser

        @Suppress("DEPRECATION")
        Handler().postDelayed(
            {
                if (currentUser != null)
                    startActivity(Intent(this, MainActivity::class.java))
                else
                    startActivity(Intent(this, StartActivity::class.java))
                finish()
            }, 2500
        )

    }
}