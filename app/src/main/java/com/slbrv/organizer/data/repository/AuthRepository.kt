package com.slbrv.organizer.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.slbrv.organizer.Config
import com.slbrv.organizer.data.auth.AuthRequestBody
import com.slbrv.organizer.data.auth.AuthResponseBody
import com.slbrv.organizer.data.auth.PublicUserDataResponseBody
import com.slbrv.organizer.data.repository.api.AuthApi
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
        authApi.signUpUser(data).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val token = response.body() ?: ""
                body.postValue(AuthResponseBody(response.code(), token))
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("APP", "onFailure() ${t.message}")
                body.postValue(AuthResponseBody(504, ""))
            }
        })
    }

    fun signIn(body: MutableLiveData<AuthResponseBody>, data: AuthRequestBody) {
        authApi.signInUser(data).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val token = response.body() ?: ""
                body.postValue(AuthResponseBody(response.code(), token))
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("APP", "onFailure() ${t.message}")
                body.postValue(AuthResponseBody(504, ""))
            }
        })
    }

    fun checkToken(tokenLiveData: MutableLiveData<String>, token: String) {
        authApi.checkToken(token).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful) {
                    when(response.code()) {
                        200 -> tokenLiveData.postValue("token$token")
                        else -> tokenLiveData.postValue("")
                    }
                } else {
                    Log.e("APP", "ERR, status: ${response.body()} token: $token")
                    tokenLiveData.postValue("")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("APP", "onFailure() ${t.message}")
                tokenLiveData.postValue("fail")
            }
        })
    }
}