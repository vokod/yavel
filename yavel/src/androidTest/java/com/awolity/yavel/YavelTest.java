package com.awolity.yavel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unused")
@RunWith(AndroidJUnit4.class)
public class YavelTest {

    private Yavel yavel;
    private final String KEY_ALIAS = "yavel key alias";

    @Before
    public void setup() throws Exception {
        yavel = Yavel.Companion.get(KEY_ALIAS);
    }

    @Test
    public void stringTest() throws Exception {
        String str = "whatever";
        String encryptedString = yavel.encryptString(str);
        yavel = null;
        yavel = Yavel.Companion.get(KEY_ALIAS);
        assertEquals(str, yavel.decryptString(encryptedString));
    }

    @Test
    public void intTest() throws Exception {
        int value = 12345;
        String encryptedInt = yavel.encryptInt(value);
        yavel = null;
        yavel = Yavel.Companion.get(KEY_ALIAS);
        assertEquals(value, yavel.decryptInt(encryptedInt));
    }

    @Test
    public void booleanTest() throws Exception {
        String encryptedValue = yavel.encryptBoolean(true);
        yavel = null;
        yavel = Yavel.Companion.get(KEY_ALIAS);
        assertTrue(yavel.decryptBoolean(encryptedValue));
    }

    @Test
    public void booleanTest2() throws Exception {
        String encryptedValue = yavel.encryptBoolean(false);
        yavel = null;
        yavel = Yavel.Companion.get(KEY_ALIAS);
        Assert.assertFalse(yavel.decryptBoolean(encryptedValue));
    }

    @Test
    public void longTest() throws Exception {
        long value = 12345678L;
        String encryptedValue = yavel.encryptLong(value);
        yavel = null;
        yavel = Yavel.Companion.get(KEY_ALIAS);
        assertEquals(value, yavel.decryptLong(encryptedValue));
    }

    @Test
    public void floatTest() throws Exception {
        float value = 1234.5678F;
        String encryptedValue = yavel.encryptFloat(value);
        yavel = null;
        yavel = Yavel.Companion.get(KEY_ALIAS);
        assertEquals(value, yavel.decryptFloat(encryptedValue), 0.00000001);
    }

    @Test
    public void doubleTest() throws Exception {
        double value = 1234.5678;
        String encryptedValue = yavel.encryptDouble(value);
        yavel = null;
        yavel = Yavel.Companion.get(KEY_ALIAS);
        assertEquals(value, yavel.decryptDouble(encryptedValue), 0.00000001);
    }
}
