package com.slbrv.organizer.data.repository.api

import com.slbrv.organizer.data.auth.AuthRequestBody
import com.slbrv.organizer.data.auth.AuthResponseBody
import com.slbrv.organizer.data.auth.PublicUserDataResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {

    @POST("/user/signup")
    fun signUpUser(@Body body: AuthRequestBody) : Call<String>

    @POST("/user/signin")
    fun signInUser(@Body body: AuthRequestBody) : Call<String>

    @GET("/user/check/{token}")
    fun checkToken(@Path("token") token: String) : Call<String>
}