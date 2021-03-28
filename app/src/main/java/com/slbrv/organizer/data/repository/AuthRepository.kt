package com.slbrv.organizer.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.slbrv.organizer.Config
import com.slbrv.organizer.data.auth.AuthBody
import com.slbrv.organizer.data.auth.AuthResponse
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
                    var body = response.body()?.token ?: ""
                    if(body.isNotEmpty()) body = "token$body"
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
                    var body = response.body()?.token ?: ""
                    if(body.isNotEmpty()) body = "token$body"
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

    fun checkToken(tokenLiveData: MutableLiveData<String>, token: String) {
        authApi.checkToken(token).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful) {
                    val code = response.code()
                    when(code) {
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