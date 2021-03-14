package com.slbrv.organizer.data.repository.api

import com.slbrv.organizer.data.entity.AuthBody
import com.slbrv.organizer.data.entity.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {

    @POST("/user/signup")
    fun signUpUser(@Body body: AuthBody) : Call<AuthResponse>

    @POST("/user/signin")
    fun signInUser(@Body body: AuthBody) : Call<AuthResponse>

    @GET("/user/check/{token}")
    fun checkToken(@Path("token") token: String) : Call<String>
}