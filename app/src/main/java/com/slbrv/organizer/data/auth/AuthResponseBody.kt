package com.slbrv.organizer.data.auth

data class AuthResponseBody(
    val status: Int,
    val token: String,
    val secret: String
)