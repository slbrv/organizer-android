package com.slbrv.organizer.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.slbrv.organizer.Config
import com.slbrv.organizer.data.entity.AuthBody
import com.slbrv.organizer.data.entity.AuthResponse
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

    fun signUp(token: MutableLiveData<String>, data: AuthBody) {
        authApi.signUpUser(data).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if(response.isSuccessful) {
                    val body = response.body()?.token ?: ""
                    token.postValue(body)
                } else {
                    token.postValue(response.code().toString())
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("APP", "onFailure() ${t.message}")
                token.postValue("")
            }
        })
    }

    fun signIn(token: MutableLiveData<String>, data: AuthBody) {
        authApi.signInUser(data).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if(response.isSuccessful) {
                    val body = response.body()?.token ?: ""
                    token.postValue(body)
                } else {
                    token.postValue(response.code().toString())
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("APP", "onFailure() ${t.message}")
                token.postValue("")
            }
        })
    }
}