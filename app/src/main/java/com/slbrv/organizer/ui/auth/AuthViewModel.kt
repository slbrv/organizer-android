package com.slbrv.organizer.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slbrv.organizer.data.auth.AuthBody
import com.slbrv.organizer.data.auth.AuthResponseBody
import com.slbrv.organizer.data.repository.AuthRepository

class AuthViewModel : ViewModel() {

    private val repo = AuthRepository()
    private val mutableToken = MutableLiveData<AuthResponseBody>()

    val body: LiveData<AuthResponseBody> get() = mutableToken

    fun signUp(data: AuthBody) {
        repo.signUp(mutableToken, data)
    }

    fun signIn(data: AuthBody) {
        repo.signIn(mutableToken, data)
    }
}