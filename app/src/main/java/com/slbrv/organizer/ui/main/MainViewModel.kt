package com.slbrv.organizer.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slbrv.organizer.data.repository.AuthRepository

class MainViewModel : ViewModel() {

    private val secretLiveData = MutableLiveData<UserSecretData>()

    private val authRepository = AuthRepository()

    fun getSecretData(prefs: SharedPreferences): LiveData<UserSecretData> {
        val token = prefs.getString("token", "") ?: ""
        val secret = prefs.getString("secret", "") ?: ""
        if(token.isEmpty() || secret.isEmpty()) {
            secretLiveData.postValue(UserSecretData())
        } else {
            authRepository.checkToken(secretLiveData, UserSecretData(token, secret))
        }
        return secretLiveData
    }
}