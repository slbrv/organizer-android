package com.slbrv.organizer.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slbrv.organizer.data.room.entity.auth.AuthBody
import com.slbrv.organizer.data.repository.AuthRepository

class AuthViewModel : ViewModel() {

    private val repo = AuthRepository()
    private val mutableToken = MutableLiveData<String>()

    val token: LiveData<String> get() = mutableToken

    fun signUp(data: AuthBody) {
        repo.signUp(mutableToken, data)
    }

    fun signIn(data: AuthBody) {
        repo.signIn(mutableToken, data)
    }
}