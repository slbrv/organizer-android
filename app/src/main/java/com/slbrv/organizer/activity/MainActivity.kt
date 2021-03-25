package com.slbrv.organizer.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.slbrv.organizer.R

class MainActivity : AppCompatActivity() {

    private val AUTH_PREFERENCES: String = "auth_data"
    private val AUTH_TOKEN: String = "token"
    private val AUTH_REQUEST_CODE = 1

    private lateinit var activeFragment: Fragment

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

        val tokenLiveData = viewModel.getToken(preferences)
        tokenLiveData.observe(this, { token ->
            if (token.isEmpty()) {
                intent = Intent(this, AuthActivity::class.java)
                startActivityForResult(intent, AUTH_REQUEST_CODE)
            } else {
                if (token == "fail") Toast.makeText(
                    this,
                    R.string.connection_error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        val token = data.getStringExtra("token") ?: ""
        if (token.isEmpty()) {
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

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
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
}