package com.example.feelbetter.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.feelbetter.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        val scale = resources.displayMetrics.density
        val padding_5dp = (5 * scale + 0.5f).toInt()
        val padding_40dp = (40 * scale + 0.5f).toInt()
        val email: EditText = findViewById(R.id.til_email)
        email.setPadding(padding_40dp, padding_5dp, padding_5dp, padding_5dp)


        btn_submit.setOnClickListener {
            val email: String = til_email.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                showMessageSnackBar(resources.getString(R.string.err_msg_enter_new_email), true)
            } else {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        hideProgressDialog()
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                resources.getString(R.string.email_sent_success),
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            showMessageSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            }
        }
    }
}