package com.awolity.yavel

import androidx.test.runner.AndroidJUnit4

import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class YavelKeyTest {

    @Test
    @Throws(Exception::class)
    fun createKey_getKey_isCorrect() {
        val yavelKey = YavelKey("whatever")

        val sKey = yavelKey.key
        assertNotNull(sKey)
    }

    @Test
    @Throws(Exception::class)
    fun hasKey_isCorrect() {
        val yavelKey = YavelKey("whatever")
        Assert.assertTrue(yavelKey.hasKey())
    }

    @Test
    @Throws(Exception::class)
    fun deleteKey_isCorrect() {
        val yavelKey = YavelKey("whatever")
        yavelKey.deleteKey()
        Assert.assertFalse(yavelKey.hasKey())
    }

    @After
    @Throws(Exception::class)
    fun deleteKey() {
        val yavelKey = YavelKey("whatever")
        yavelKey.deleteKey()
    }
}
