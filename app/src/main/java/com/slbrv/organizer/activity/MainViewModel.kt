package com.slbrv.organizer.activity

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slbrv.organizer.data.repository.AuthRepository

class MainViewModel : ViewModel() {

    private val tokenLiveData = MutableLiveData<String>()

    private val authRepository = AuthRepository()

    fun getToken(prefs: SharedPreferences): LiveData<String> {
        val token = prefs.getString("token", "") ?: ""
        if(token.isEmpty()) {
            tokenLiveData.postValue(token)
        } else {
            authRepository.checkToken(tokenLiveData, token)
        }

        return tokenLiveData
    }
}