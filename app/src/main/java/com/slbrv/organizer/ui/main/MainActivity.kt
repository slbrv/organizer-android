package com.slbrv.organizer.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.slbrv.organizer.R
import com.slbrv.organizer.ui.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    private val AUTH_PREFERENCES: String = "auth_data"
    private val AUTH_TOKEN: String = "token"
    private val AUTH_SECRET: String = "secret"
    private val AUTH_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)

        val preferences: SharedPreferences = getSharedPreferences(
            AUTH_PREFERENCES,
            Context.MODE_PRIVATE
        )

        val viewModel: MainViewModel by viewModels()

        val secretLiveData = viewModel.getSecretData(preferences)
        secretLiveData.observe(this, { data ->
            // TODO Offline mode
            if (data.isEmpty()) {
                intent = Intent(this, AuthActivity::class.java)
                startActivityForResult(intent, AUTH_REQUEST_CODE)
            } else {
                Log.i("APP", "Token: ${data.token}")
                Log.i("APP", "Secret: ${data.secret}")
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        val token = data.getStringExtra(AUTH_TOKEN) ?: ""
        val secret = data.getStringExtra(AUTH_SECRET) ?: ""
        if (token.isEmpty() || token.isEmpty()) {
            Toast.makeText(this, R.string.auth_error, Toast.LENGTH_LONG).show()
        } else {
            getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putString(AUTH_TOKEN, token)
                .putString(AUTH_SECRET, secret)
                .apply()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_logout -> {
            getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .remove(AUTH_TOKEN)
                .remove(AUTH_SECRET)
                .apply()
            intent = Intent(this, AuthActivity::class.java)
            startActivityForResult(intent, AUTH_REQUEST_CODE)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}