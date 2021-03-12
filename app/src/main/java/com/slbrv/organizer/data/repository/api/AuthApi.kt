package com.slbrv.organizer.data.repository.api

import com.slbrv.organizer.data.entity.AuthBody
import com.slbrv.organizer.data.entity.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/user/signup")
    fun signUpUser(@Body body: AuthBody) : Call<AuthResponse>

    @POST("/user/signin")
    fun signInUser(@Body body: AuthBody) : Call<AuthResponse>
}