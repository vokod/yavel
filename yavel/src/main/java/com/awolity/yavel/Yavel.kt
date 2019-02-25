package com.awolity.yavel

import java.util.HashMap

import javax.crypto.SecretKey

class Yavel @Throws(YavelException::class)
private constructor(keyAlias: String) {

    private val yavelKey: YavelKey
    private val secretKey: SecretKey

    init {
        try {
            yavelKey = YavelKey(keyAlias)
            secretKey = yavelKey.key
        } catch (e: YavelKeyException) {
            throw YavelException(e)
        }
    }

    @Throws(YavelException::class)
    fun decryptString(encryptedString: String): String {
        return CryptUtils.decryptString(encryptedString, secretKey)
    }

    @Throws(YavelException::class)
    fun encryptString(plainString: String): String {
        return CryptUtils.encryptString(plainString, secretKey)
    }

    @Throws(YavelException::class)
    fun decryptBoolean(encryptedBoolean: String): Boolean {
        return CryptUtils.decryptBoolean(encryptedBoolean, secretKey)
    }

    @Throws(YavelException::class)
    fun encryptBoolean(plainValue: Boolean): String {
        return CryptUtils.encryptBoolean(plainValue, secretKey)
    }

    @Throws(YavelException::class)
    fun decryptFloat(encryptedFloat: String): Float {
        return CryptUtils.decryptFloat(encryptedFloat, secretKey)
    }

    @Throws(YavelException::class)
    fun encryptFloat(plainValue: Float): String {
        return CryptUtils.encryptFloat(plainValue, secretKey)
    }

    @Throws(YavelException::class)
    fun decryptDouble(encryptedValue: String): Double {
        return CryptUtils.decryptDouble(encryptedValue, secretKey)
    }

    @Throws(YavelException::class)
    fun encryptDouble(plainValue: Double): String {
        return CryptUtils.encryptDouble(plainValue, secretKey)
    }

    @Throws(YavelException::class)
    fun decryptInt(encryptedInt: String): Int {
        return CryptUtils.decryptInt(encryptedInt, secretKey)
    }

    @Throws(YavelException::class)
    fun encryptInt(plainValue: Int): String {
        return CryptUtils.encryptInt(plainValue, secretKey)
    }

    @Throws(YavelException::class)
    fun decryptLong(encryptedLong: String): Long {
        return CryptUtils.decryptLong(encryptedLong, secretKey)
    }

    @Throws(YavelException::class)
    fun encryptLong(plainValue: Long): String {
        return CryptUtils.encryptLong(plainValue, secretKey)
    }

    companion object {

        private var yavels: MutableMap<String, Yavel>? = null

        @Throws(YavelException::class)
        fun get(keyAlias: String): Yavel {
            if (yavels == null) { // if no yavels were created
                yavels = HashMap(1)
            }
            return if (yavels!!.containsKey(keyAlias)) { // if the yavel in question is in yavels
                yavels!![keyAlias]!!
            } else { // if the yavel in question is NOT in the yavels
                yavels!![keyAlias] = Yavel(keyAlias)
                yavels!![keyAlias]!!
            }
        }
    }
    // TODO: serializable, parcelable
}
