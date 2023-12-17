package com.spacewalk.utils

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object AES256Util {
    const val ALGORITHM = "AES/CBC/PKCS5Padding"
    private const val key = "32f69d3ab1ae4759aac0b785e0b345ea" //salt, don't change
    private val iv = key.substring(0, 16) // 16byte
    @Throws(Exception::class)
    fun encrypt(text: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val keySpec = SecretKeySpec(key.toByteArray(), "AES")
        val ivParamSpec = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec)
        val encrypted = cipher.doFinal(text.toByteArray(charset("UTF-8")))
        return Base64.getEncoder().encodeToString(encrypted)
    }

    @Throws(Exception::class)
    fun decrypt(cipherText: String?): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val keySpec = SecretKeySpec(key.toByteArray(), "AES")
        val ivParamSpec = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec)
        val decodedBytes = Base64.getDecoder().decode(cipherText)
        val decrypted = cipher.doFinal(decodedBytes)
        return String(decrypted, charset("UTF-8"))
    }
}

