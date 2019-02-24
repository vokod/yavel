package com.awolity.yavel

import android.util.Base64

import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

internal object CryptUtils {

    private const val IV_LENGTH_IN_BYTES = 12 // GCM mode cipher only accepts 12 byte IV
    private const val GCM_AUTH_TAG_LENGTH_IN_BITS = 128
    private const val AAD = "Insignificant2"
    private const val AES_GCM_NOPADDING = "AES/GCM/NoPadding"
    private const val TRUE = "TRUE"
    private const val FALSE = "FALSE"

    @Throws(YavelException::class)
    fun encryptString(plainText: String, key: SecretKey): String {
        val plainArray = plainText.toByteArray()
        val encryptedArray = encryptByteArray(plainArray, key)
        val encryptedString = Base64.encodeToString(encryptedArray, Base64.DEFAULT)
        return removeNewLines(encryptedString)
    }

    @Throws(YavelException::class)
    fun decryptString(encryptedString: String, key: SecretKey): String {
        val encryptedArray = Base64.decode(encryptedString, Base64.DEFAULT)
        val decryptedArray = decryptByteArray(encryptedArray, key)
        val decryptedString = String(decryptedArray)
        return removeNewLines(decryptedString)
    }

    @Throws(YavelException::class)
    fun encryptBoolean(value: Boolean, key: SecretKey): String {
        return if (value) {
            encryptString(TRUE, key)
        } else {
            encryptString(FALSE, key)
        }
    }

    @Throws(YavelException::class)
    fun decryptBoolean(encryptedBoolean: String, key: SecretKey): Boolean {
        val decryptedBoolean = decryptString(encryptedBoolean, key)
        return when (decryptedBoolean) {
            TRUE -> true
            FALSE -> false
            else -> throw YavelException(Throwable("Cannot decrypt boolean"))
        }
    }

    @Throws(YavelException::class)
    fun encryptFloat(floatValue: Float, key: SecretKey): String {
        val floatAsString = java.lang.Float.toString(floatValue)
        return encryptString(floatAsString, key)
    }

    @Throws(YavelException::class)
    fun decryptFloat(encryptedFloat: String, key: SecretKey): Float {
        val decryptedFloatAsString = decryptString(encryptedFloat, key)
        return java.lang.Float.parseFloat(decryptedFloatAsString)
    }

    @Throws(YavelException::class)
    fun encryptDouble(value: Double, key: SecretKey): String {
        val doubleAsString = java.lang.Double.toString(value)
        return encryptString(doubleAsString, key)
    }

    @Throws(YavelException::class)
    fun decryptDouble(encryptedValue: String, key: SecretKey): Double {
        val decryptedDoubleAsString = decryptString(encryptedValue, key)
        return java.lang.Double.parseDouble(decryptedDoubleAsString)
    }

    @Throws(YavelException::class)
    fun encryptLong(longValue: Long, key: SecretKey): String {
        val valueAsString = java.lang.Long.toString(longValue)
        return encryptString(valueAsString, key)
    }

    @Throws(YavelException::class)
    fun decryptLong(encryptedLongAsString: String, key: SecretKey): Long {
        val decryptedLongAsString = decryptString(encryptedLongAsString, key)
        return java.lang.Long.parseLong(decryptedLongAsString)
    }

    @Throws(YavelException::class)
    fun encryptInt(intValue: Int, key: SecretKey): String {
        val intValueAsString = Integer.toString(intValue)
        return encryptString(intValueAsString, key)
    }

    @Throws(YavelException::class)
    fun decryptInt(encryptedIntAsString: String, key: SecretKey): Int {
        val decryptedIntAsString = decryptString(encryptedIntAsString, key)
        return Integer.parseInt(decryptedIntAsString)
    }

    @Throws(YavelException::class)
    private fun encryptByteArray(plainArray: ByteArray, key: SecretKey): ByteArray {
        val result: ByteArray
        val aad = AAD.toByteArray()

        val cipher: Cipher
        try {
            cipher = Cipher.getInstance(AES_GCM_NOPADDING)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            cipher.updateAAD(aad)
            val iv = cipher.iv
            val encryptedBytes = cipher.doFinal(plainArray)
            result = concatenateByteArrays(iv, encryptedBytes)


        } catch (e: NoSuchPaddingException) {
            throw YavelException(e)
        } catch (e: NoSuchAlgorithmException) {
            throw YavelException(e)
        } catch (e: InvalidKeyException) {
            throw YavelException(e)
        } catch (e: IllegalBlockSizeException) {
            throw YavelException(e)
        } catch (e: BadPaddingException) {
            throw YavelException(e)
        }

        return result
    }

    @Throws(YavelException::class)
    private fun decryptByteArray(encryptedArray: ByteArray, key: SecretKey): ByteArray {

        val plainArray: ByteArray
        val iv = ByteArray(IV_LENGTH_IN_BYTES)
        val encryptedBytes = ByteArray(encryptedArray.size - IV_LENGTH_IN_BYTES)
        val aad = AAD.toByteArray()

        System.arraycopy(encryptedArray, 0, iv, 0, IV_LENGTH_IN_BYTES)
        System.arraycopy(encryptedArray, IV_LENGTH_IN_BYTES, encryptedBytes, 0, encryptedBytes.size)

        val cipher: Cipher
        try {
            val gcmParameterSpec = GCMParameterSpec(GCM_AUTH_TAG_LENGTH_IN_BITS, iv)
            cipher = Cipher.getInstance(AES_GCM_NOPADDING)
            cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec)
            cipher.updateAAD(aad)
            plainArray = cipher.doFinal(encryptedBytes)

        } catch (e: IllegalBlockSizeException) {
            throw YavelException(e)
        } catch (e: BadPaddingException) {
            throw YavelException(e)
        } catch (e: NullPointerException) {
            throw YavelException(e)
        } catch (e: NoSuchPaddingException) {
            throw YavelException(e)
        } catch (e: NoSuchAlgorithmException) {
            throw YavelException(e)
        } catch (e: InvalidKeyException) {
            throw YavelException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw YavelException(e)
        }

        return plainArray
    }

    private fun concatenateByteArrays(first: ByteArray, second: ByteArray): ByteArray {
        val result = ByteArray(first.size + second.size)
        System.arraycopy(first, 0, result, 0, first.size)
        System.arraycopy(second, 0, result, first.size, second.size)
        return result
    }

    private fun removeNewLines(input: String): String {
        var result = input.replace("\n", "")
        result = result.replace("\r", "")
        return result
    }
}
