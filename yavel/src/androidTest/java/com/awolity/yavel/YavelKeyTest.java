package com.awolity.yavel;

import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.crypto.SecretKey;

import static org.junit.Assert.*;

@SuppressWarnings("unused")
@RunWith(AndroidJUnit4.class)
public class YavelKeyTest {

    @Test
    public void createKey_getKey_isCorrect() throws Exception {
        YavelKey yavelKey = new YavelKey("whatever");

        SecretKey sKey = yavelKey.getKey();
        assertNotNull(sKey);
    }

    @Test
    public void hasKey_isCorrect() throws Exception {
        YavelKey yavelKey = new YavelKey("whatever");
        Assert.assertTrue(yavelKey.hasKey());
    }

    @Test
    public void deleteKey_isCorrect() throws Exception {
        YavelKey yavelKey = new YavelKey("whatever");
        yavelKey.deleteKey();
        Assert.assertFalse(yavelKey.hasKey());
    }

    @After
    public void deleteKey() throws Exception{
        YavelKey yavelKey = new YavelKey("whatever");
        yavelKey.deleteKey();
    }
}
