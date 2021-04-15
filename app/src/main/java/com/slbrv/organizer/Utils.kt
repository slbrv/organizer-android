package com.slbrv.organizer

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Utils {

    fun md5(input: String): String = hash("MD5", input)

    private fun hash(algo: String, input: String): String {
        try {
            val digest = MessageDigest
                .getInstance(algo)
            digest.update(input.toByteArray())
            val messageDigest = digest.digest()

            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }
}