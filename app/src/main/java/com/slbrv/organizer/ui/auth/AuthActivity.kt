package com.slbrv.organizer.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.slbrv.organizer.R
import com.slbrv.organizer.data.auth.AuthResponseBody

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if (savedInstanceState == null) {
            setSignUpFragment()
        }

        val viewModel: AuthViewModel by viewModels()
        val body = viewModel.body
        body.observe(this, {
            observe(it)
        })
    }

    override fun onBackPressed() {}

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

    private fun observe(body: AuthResponseBody) {
        when (body.status) {
            200, 201 -> success(body)
            400 -> Toast.makeText(
                this,
                R.string.user_with_this_username_or_email_already_exists,
                Toast.LENGTH_SHORT
            ).show()
            403 -> Toast.makeText(
                this,
                R.string.incorrect_username_or_password,
                Toast.LENGTH_SHORT
            ).show()
            else -> {
                Toast.makeText(
                    this,
                    R.string.auth_error,
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("APP", "Body ${body.token}")
            }
        }
    }

    private fun success(body: AuthResponseBody) {
        intent = Intent()
        intent.putExtra("token", body.token)
        intent.putExtra("secret", body.secret)
        setResult(RESULT_OK, intent)
        finish()
    }
}