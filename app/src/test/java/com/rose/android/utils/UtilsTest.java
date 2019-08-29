package com.rose.android.utils;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by wenen on 2017/12/25.
 */
public class UtilsTest {
    @Test
    public void parseStrToMd5L32() throws Exception {
        assertEquals(32, Utils.parseStrToMd5L32("wmj141519").length());
    }

    @Test
    public void addUnitWhenBeyondThousand() throws Exception {
        assertEquals("1.00k", Utils.addUnitWhenBeyondThousand(new BigDecimal(1000)));
    }

    @Test
    public void formatWithScale() throws Exception {
        assertEquals("6.00", Utils.formatWithScale(6.00001, 2));
    }

    @Test
    public void formatWithScale1() throws Exception {
        assertEquals("6.00", Utils.formatWithScale(6.00001, 2));
    }

    @Test
    public void formatWithScale2() throws Exception {
        assertEquals("6.00", Utils.formatWithScale(new BigDecimal(6.0001), 2));
    }

    @Test
    public void formatWithThousandsSeparator() throws Exception {
        assertEquals("10,000.00", Utils.formatWithThousandsSeparator(10000));
    }

    @Test
    public void formatWithThousandsSeparator1() throws Exception {
        assertEquals("10,000.00", Utils.formatWithThousandsSeparator(10000));
    }

    @Test
    public void formatWithThousandsSeparator2() throws Exception {
        assertEquals("10,000.00", Utils.formatWithThousandsSeparator(new BigDecimal(10000)));
    }

    @Test
    public void isPhoneValid() throws Exception {
        assertEquals(true, Utils.isPhoneValid("17600000000"));
    }

    @Test
    public void isPasswordValid() throws Exception {
        assertEquals(true, Utils.isPasswordValid("qaz123"));
    }

}
