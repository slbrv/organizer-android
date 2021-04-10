package com.slbrv.organizer.ui.main

data class UserSecretData(
    val token: String = "",
    val secret: String = ""
) {
    fun isEmpty() : Boolean = token.isEmpty() || secret.isEmpty()
}