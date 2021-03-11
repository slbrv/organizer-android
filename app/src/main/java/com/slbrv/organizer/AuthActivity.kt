package com.slbrv.organizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.RoomDatabase
import com.slbrv.organizer.ui.auth.SignInFragment
import com.slbrv.organizer.ui.auth.SignUpFragment

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if (savedInstanceState == null) {
            setSignUpFragment()
        }
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