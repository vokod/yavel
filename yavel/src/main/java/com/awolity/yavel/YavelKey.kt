package com.awolity.yavel

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

internal class YavelKey @Throws(YavelKeyException::class)
constructor(private val alias: String) {

    val key: SecretKey
        @Throws(YavelKeyException::class)
        get() {
            val keyStore = initKeyStore()
            try {
                return keyStore.getKey(alias, null) as SecretKey
            } catch (e: KeyStoreException) {
                throw YavelKeyException(e)
            } catch (e: NoSuchAlgorithmException) {
                throw YavelKeyException(e)
            } catch (e: UnrecoverableKeyException) {
                throw YavelKeyException(e)
            }

        }

    init {
        if (!hasKey()) {
            createKey()
        }
    }

    @Throws(YavelKeyException::class)
    fun createKey() {
        val keyStore: KeyStore

        try {
            keyStore = initKeyStore()

            if (!keyStore.containsAlias(alias)) {

                val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,
                        ANDROID_KEYSTORE_PROVIDER)

                val builder = KeyGenParameterSpec.Builder(
                        alias,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                        .setKeySize(AES_KEY_LENGTH_IN_BITS)
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setUserAuthenticationRequired(false)

                generator.init(builder.build())
                generator.generateKey()
            }
        } catch (e: NoSuchAlgorithmException) {
            throw YavelKeyException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw YavelKeyException(e)
        } catch (e: NoSuchProviderException) {
            throw YavelKeyException(e)
        } catch (e: KeyStoreException) {
            throw YavelKeyException(e)
        }
    }

    @Throws(YavelKeyException::class)
    fun deleteKey() {
        val keyStore: KeyStore

        try {
            keyStore = initKeyStore()

            if (keyStore.containsAlias(alias)) {
                keyStore.deleteEntry(alias)
            }
        } catch (e: KeyStoreException) {
            throw YavelKeyException(e)
        }
    }

    @Throws(YavelKeyException::class)
    fun hasKey(): Boolean {
        var result = false

        val keyStore = initKeyStore()
        try {
            if (keyStore.containsAlias(alias)) {
                result = true
            }
        } catch (e: KeyStoreException) {
            throw YavelKeyException(e)
        }
        return result
    }

    @Throws(YavelKeyException::class)
    private fun initKeyStore(): KeyStore {
        val keyStore: KeyStore
        try {
            keyStore = KeyStore.getInstance(ANDROID_KEYSTORE_PROVIDER)
            keyStore.load(null)
        } catch (e: KeyStoreException) {
            throw YavelKeyException(e)
        } catch (e: IOException) {
            throw YavelKeyException(e)
        } catch (e: NoSuchAlgorithmException) {
            throw YavelKeyException(e)
        } catch (e: CertificateException) {
            throw YavelKeyException(e)
        }
        return keyStore
    }

    companion object {
        private const val ANDROID_KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val AES_KEY_LENGTH_IN_BITS = 256
    }
}
