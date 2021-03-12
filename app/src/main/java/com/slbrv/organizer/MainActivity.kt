package com.slbrv.organizer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    private val AUTH_PREFERENCES: String = "auth_data"
    private val AUTH_TOKEN: String = "token"
    private val AUTH_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_notes, R.id.navigation_tasks, R.id.navigation_alarms
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val preferences: SharedPreferences = getSharedPreferences(
            AUTH_PREFERENCES,
            Context.MODE_PRIVATE
        )

        val token = preferences.getString(AUTH_TOKEN, "") ?: ""
        if(token.isEmpty()) {
            intent = Intent(this, AuthActivity::class.java)
            startActivityForResult(intent, AUTH_REQUEST_CODE)
        } else {
            setTasksFragment()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data == null) return
        val token = data.getStringExtra("token") ?: ""
        if(token.isEmpty()) {
            Toast.makeText(this, R.string.auth_error, Toast.LENGTH_LONG).show()
        } else {
            getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .putString(AUTH_TOKEN, token)
                .apply()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_logout -> {
            getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE)
                .edit()
                .remove(AUTH_TOKEN)
                .apply()
            intent = Intent(this, AuthActivity::class.java)
            startActivityForResult(intent, AUTH_REQUEST_CODE)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setNotesFragment() {

    }

    private fun setTasksFragment() {

    }

    private fun setAlarmsFragment() {

    }
}