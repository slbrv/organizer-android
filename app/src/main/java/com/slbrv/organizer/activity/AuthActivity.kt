package com.slbrv.organizer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.slbrv.organizer.R
import com.slbrv.organizer.ui.auth.SignUpFragment
import com.slbrv.organizer.ui.auth.AuthViewModel

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if (savedInstanceState == null) {
            setSignUpFragment()
        }

        val viewModel: AuthViewModel by viewModels()
        val token = viewModel.token
        token.observe(this, { body ->
            run {
                when {
                    body == "400" -> Toast.makeText(
                        this,
                        R.string.user_with_this_username_or_email_already_exists,
                        Toast.LENGTH_SHORT
                    ).show()
                    body == "403" -> Toast.makeText(
                        this,
                        R.string.incorrect_username_or_password,
                        Toast.LENGTH_SHORT
                    ).show()
                    body.length < 5 -> {
                        Toast.makeText(
                            this,
                            R.string.auth_error,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("APP", "Body $body")
                    }
                    body.substring(0, 5) == "token" -> {
                        intent = Intent()
                        intent.putExtra("token", body.substring(5))
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                    else -> {
                        Toast.makeText(
                            this,
                            R.string.auth_error,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("APP", "Body $body")
                    }
                }
            }
        })
    }

    override fun onBackPressed() {

    }

    private fun setSignUpFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.activity_main_container,
                SignUpFragment.newInstance(),
                "sign_up_fragment"
            )
            .commitNow()
    }
}