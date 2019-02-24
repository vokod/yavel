package com.awolity.yavel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CryptUtilsTest {

    private YavelKey yavelKey;

    @Before
    public void initKey()  throws Exception{
        yavelKey = new YavelKey("whatever");
    }

    @After
    public void deleteKey()  throws Exception{
        yavelKey.deleteKey();
    }

    @Test
    public void encryptString_decryptString() throws Exception {
        String plainString = "whatever1234";
        String encryptedString = CryptUtils.INSTANCE.encryptString(plainString, yavelKey.getKey());
        String decryptedString = CryptUtils.INSTANCE.decryptString(encryptedString, yavelKey.getKey());

        assertEquals(plainString, decryptedString);
    }

    @Test
    public void encryptString_decryptString2() throws Exception {
        String plainString = "whatever1234";
        String encryptedString = CryptUtils.INSTANCE.encryptString(plainString, yavelKey.getKey());

        yavelKey = new YavelKey("whatever2");

        try {
            @SuppressWarnings("UnusedAssignment") String decryptedString = CryptUtils.INSTANCE.decryptString(encryptedString, yavelKey.getKey());
        } catch (YavelException e) {
            assertEquals("javax.crypto.AEADBadTagException", e.getMessage());
        }
    }

    @Test
    public void encryptBoolean_decryptBoolean() throws Exception {
        String encryptedBoolean = CryptUtils.INSTANCE.encryptBoolean(true, yavelKey.getKey());
        boolean decryptedBoolean = CryptUtils.INSTANCE.decryptBoolean(encryptedBoolean, yavelKey.getKey());

        assertTrue(decryptedBoolean);
    }

    @Test
    public void encryptBoolean_decryptBoolean2() throws Exception {
        String encryptedBoolean = CryptUtils.INSTANCE.encryptBoolean(false, yavelKey.getKey());
        boolean decryptedBoolean = CryptUtils.INSTANCE.decryptBoolean(encryptedBoolean, yavelKey.getKey());

        assertFalse(decryptedBoolean);
    }

    @Test
    public void encryptFloat_decryptFloat() throws Exception {
        float value = (float) 6.836542E-8;
        String encryptedFloat = CryptUtils.INSTANCE.encryptFloat(value, yavelKey.getKey());
        float decryptedFloat = CryptUtils.INSTANCE.decryptFloat(encryptedFloat, yavelKey.getKey());

        assertEquals(value, decryptedFloat,0.0001F);
    }

    @Test
    public void encryptDouble_decryptDouble() throws Exception {
        double value = 6.836542E-8;
        String encryptedValue = CryptUtils.INSTANCE.encryptDouble(value, yavelKey.getKey());
        double decryptedValue = CryptUtils.INSTANCE.decryptFloat(encryptedValue, yavelKey.getKey());

        assertEquals(value, decryptedValue,0.0001F);
    }

    @Test
    public void encryptLong_decryptLong() throws Exception {
        long value = 654987321;
        String encryptedLong = CryptUtils.INSTANCE.encryptLong(value, yavelKey.getKey());
        float decryptedLong = CryptUtils.INSTANCE.decryptLong(encryptedLong, yavelKey.getKey());

        assertEquals(value, decryptedLong,1);
    }

    @Test
    public void encryptInt_decryptInt() throws Exception {
        int value = 654987;
        String encryptedInt = CryptUtils.INSTANCE.encryptInt(value, yavelKey.getKey());
        float decryptedInt = CryptUtils.INSTANCE.decryptInt(encryptedInt, yavelKey.getKey());

        assertEquals(value, decryptedInt,1);
    }
}
