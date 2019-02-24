package com.awolity.yavel

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

class CryptUtilsTest {

    private var yavelKey: YavelKey? = null

    @Before
    @Throws(Exception::class)
    fun initKey() {
        yavelKey = YavelKey("whatever")
    }

    @After
    @Throws(Exception::class)
    fun deleteKey() {
        yavelKey!!.deleteKey()
    }

    @Test
    @Throws(Exception::class)
    fun encryptString_decryptString() {
        val plainString = "whatever1234"
        val encryptedString = CryptUtils.encryptString(plainString, yavelKey!!.key)
        val decryptedString = CryptUtils.decryptString(encryptedString, yavelKey!!.key)

        assertEquals(plainString, decryptedString)
    }

    @Test
    @Throws(Exception::class)
    fun encryptString_decryptString2() {
        val plainString = "whatever1234"
        val encryptedString = CryptUtils.encryptString(plainString, yavelKey!!.key)

        yavelKey = YavelKey("whatever2")

        try {
            val decryptedString = CryptUtils.decryptString(encryptedString, yavelKey!!.key)
        } catch (e: YavelException) {
            assertEquals("javax.crypto.AEADBadTagException", e.message)
        }
    }

    @Test
    @Throws(Exception::class)
    fun encryptBoolean_decryptBoolean() {
        val encryptedBoolean = CryptUtils.encryptBoolean(true, yavelKey!!.key)
        val decryptedBoolean = CryptUtils.decryptBoolean(encryptedBoolean, yavelKey!!.key)

        assertTrue(decryptedBoolean)
    }

    @Test
    @Throws(Exception::class)
    fun encryptBoolean_decryptBoolean2() {
        val encryptedBoolean = CryptUtils.encryptBoolean(false, yavelKey!!.key)
        val decryptedBoolean = CryptUtils.decryptBoolean(encryptedBoolean, yavelKey!!.key)

        assertFalse(decryptedBoolean)
    }

    @Test
    @Throws(Exception::class)
    fun encryptFloat_decryptFloat() {
        val value = 6.836542E-8.toFloat()
        val encryptedFloat = CryptUtils.encryptFloat(value, yavelKey!!.key)
        val decryptedFloat = CryptUtils.decryptFloat(encryptedFloat, yavelKey!!.key)

        assertEquals(value, decryptedFloat, 0.0001f)
    }

    @Test
    @Throws(Exception::class)
    fun encryptDouble_decryptDouble() {
        val value = 6.836542E-8
        val encryptedValue = CryptUtils.encryptDouble(value, yavelKey!!.key)
        val decryptedValue = CryptUtils.decryptFloat(encryptedValue, yavelKey!!.key).toDouble()

        assertEquals(value, decryptedValue, 0.0001)
    }

    @Test
    @Throws(Exception::class)
    fun encryptLong_decryptLong() {
        val value: Long = 654987321
        val encryptedLong = CryptUtils.encryptLong(value, yavelKey!!.key)
        val decryptedLong = CryptUtils.decryptLong(encryptedLong, yavelKey!!.key).toFloat()

        assertEquals(value.toFloat(), decryptedLong, 1f)
    }

    @Test
    @Throws(Exception::class)
    fun encryptInt_decryptInt() {
        val value = 654987
        val encryptedInt = CryptUtils.encryptInt(value, yavelKey!!.key)
        val decryptedInt = CryptUtils.decryptInt(encryptedInt, yavelKey!!.key).toFloat()

        assertEquals(value.toFloat(), decryptedInt, 1f)
    }
}
