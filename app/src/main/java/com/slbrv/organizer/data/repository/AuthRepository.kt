package com.slbrv.organizer.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.slbrv.organizer.Config
import com.slbrv.organizer.util.Algorithm
import com.slbrv.organizer.data.auth.AuthRequestBody
import com.slbrv.organizer.data.auth.AuthResponseBody
import com.slbrv.organizer.data.repository.api.AuthApi
import com.slbrv.organizer.ui.main.UserSecretData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthRepository {

    private val gson = GsonBuilder()
        .setLenient()
        .create()
    private val retrofit = Retrofit.Builder()
        .baseUrl(Config.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val authApi = retrofit.create(AuthApi::class.java)

    fun signUp(body: MutableLiveData<AuthResponseBody>, data: AuthRequestBody) {
        authApi
            .signUpUser(data)
            .enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val token = response.body() ?: ""
                val mix = data.username + data.password
                body.postValue(AuthResponseBody(response.code(), token, getSecret(mix)))
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("APP", "onFailure() ${t.message}")
                body.postValue(AuthResponseBody(504, "", ""))
            }
        })
    }

    fun signIn(body: MutableLiveData<AuthResponseBody>, data: AuthRequestBody) {
        authApi
            .signInUser(data)
            .enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val token = response.body() ?: ""
                val mix = data.username + data.password
                body.postValue(AuthResponseBody(response.code(), token, getSecret(mix)))
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("APP", "onFailure() ${t.message}")
                body.postValue(AuthResponseBody(504, "", ""))
            }
        })
    }

    fun checkToken(secretLiveData: MutableLiveData<UserSecretData>, data: UserSecretData) {
        authApi
            .checkToken(data.token)
            .enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful) {
                    when(response.code()) {
                        200 -> secretLiveData.postValue(data)
                        else -> secretLiveData.postValue(UserSecretData())
                    }
                } else {
                    Log.e("APP", "ERR, status: ${response.body()} token: ${data.token}")
                    secretLiveData.postValue(UserSecretData())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("APP", "onFailure() ${t.message}")
                secretLiveData.postValue(UserSecretData())
            }
        })
    }

    private fun getSecret(mix: String) = Algorithm.md5(mix)
}