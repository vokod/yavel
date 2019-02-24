package com.awolity.yavel

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.runner.AndroidJUnit4

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@RunWith(AndroidJUnit4::class)
class YavelTest {

    private var yavel: Yavel? = null
    private val KEY_ALIAS = "yavel key alias"

    @Before
    @Throws(Exception::class)
    fun setup() {
        yavel = Yavel[KEY_ALIAS]
    }

    @Test
    @Throws(Exception::class)
    fun stringTest() {
        val str = "whatever"
        val encryptedString = yavel!!.encryptString(str)
        yavel = null
        yavel = Yavel[KEY_ALIAS]
        assertEquals(str, yavel!!.decryptString(encryptedString))
    }

    @Test
    @Throws(Exception::class)
    fun intTest() {
        val value = 12345
        val encryptedInt = yavel!!.encryptInt(value)
        yavel = null
        yavel = Yavel[KEY_ALIAS]
        assertEquals(value.toLong(), yavel!!.decryptInt(encryptedInt).toLong())
    }

    @Test
    @Throws(Exception::class)
    fun booleanTest() {
        val encryptedValue = yavel!!.encryptBoolean(true)
        yavel = null
        yavel = Yavel[KEY_ALIAS]
        assertTrue(yavel!!.decryptBoolean(encryptedValue))
    }

    @Test
    @Throws(Exception::class)
    fun booleanTest2() {
        val encryptedValue = yavel!!.encryptBoolean(false)
        yavel = null
        yavel = Yavel[KEY_ALIAS]
        Assert.assertFalse(yavel!!.decryptBoolean(encryptedValue))
    }

    @Test
    @Throws(Exception::class)
    fun longTest() {
        val value = 12345678L
        val encryptedValue = yavel!!.encryptLong(value)
        yavel = null
        yavel = Yavel[KEY_ALIAS]
        assertEquals(value, yavel!!.decryptLong(encryptedValue))
    }

    @Test
    @Throws(Exception::class)
    fun floatTest() {
        val value = 1234.5678f
        val encryptedValue = yavel!!.encryptFloat(value)
        yavel = null
        yavel = Yavel[KEY_ALIAS]
        assertEquals(value.toDouble(), yavel!!.decryptFloat(encryptedValue).toDouble(), 0.00000001)
    }

    @Test
    @Throws(Exception::class)
    fun doubleTest() {
        val value = 1234.5678
        val encryptedValue = yavel!!.encryptDouble(value)
        yavel = null
        yavel = Yavel[KEY_ALIAS]
        assertEquals(value, yavel!!.decryptDouble(encryptedValue), 0.00000001)
    }
}
