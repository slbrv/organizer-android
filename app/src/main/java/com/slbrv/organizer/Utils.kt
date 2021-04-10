package com.slbrv.organizer

import java.security.MessageDigest
import java.util.*

object Utils {

    fun md5(input: String): String = hash("MD5", input)

    private fun hash(algo: String, input: String): String {
        val bytes = MessageDigest
            .getInstance(algo)
            .digest(input.toByteArray())
        return String(bytes)
    }
}