package com.slbrv.organizer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.slbrv.organizer.ui.auth.SignInFragment
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
                when (body) {
                    "400" -> Toast.makeText(
                        this,
                        R.string.user_with_this_username_or_email_already_exists,
                        Toast.LENGTH_LONG
                    ).show()
                    "null", "" -> Toast.makeText(
                        this,
                        R.string.user_with_this_username_or_email_already_exists,
                        Toast.LENGTH_LONG
                    ).show()
                    else -> {
                        intent = Intent()
                        intent.putExtra("token", body)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }
            }
        })
    }

    override fun onBackPressed() {

    }

    private fun setSignInFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SignInFragment.newInstance())
            .commitNow()
    }

    private fun setSignUpFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SignUpFragment.newInstance())
            .commitNow()
    }
}