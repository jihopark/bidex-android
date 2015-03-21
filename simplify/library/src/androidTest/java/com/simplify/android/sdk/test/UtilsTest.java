package com.simplify.android.sdk.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.simplify.android.sdk.Utils;
import com.simplify.android.sdk.model.Card;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class UtilsTest extends TestCase
{
    @SmallTest
    public void testInputStreamToString()
    {
        // init a fake stream
        String content = "here is some test content";
        InputStream is = new ByteArrayInputStream(content.getBytes());

        // test parsing back into string
        try {
            assertEquals(Utils.inputStreamToString(is), content);
        }
        catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @SmallTest
    public void testValidateCardNumber()
    {
        // test good number
        String number = "5555 5555 5555 4444";
        assertTrue(Utils.validateCardNumber(number, Card.Type.MASTERCARD));

        // test wrong type
        assertFalse(Utils.validateCardNumber(number, Card.Type.VISA));

        // test number too short
        assertFalse(Utils.validateCardNumber(number.substring(0, number.length() - 3), Card.Type.MASTERCARD));

        // test invalid luhn check
        number = "5555 2894 2838 8302";
        assertFalse(Utils.validateCardNumber(number, Card.Type.MASTERCARD));
    }

    @SmallTest
    public void testValidateCardExpiration()
    {
        // test missing data
        assertFalse(Utils.validateCardExpiration("", ""));

        // test zeros
        assertFalse(Utils.validateCardExpiration(0, 0));

        // test good strings
        assertTrue(Utils.validateCardExpiration("4", "50"));

        // test good 2-digit integers
        assertTrue(Utils.validateCardExpiration(4, 50));

        // test good 4-digit integers
        assertTrue(Utils.validateCardExpiration(4, 2050));

        // test date in the past
        assertFalse(Utils.validateCardExpiration(4, 2012));
    }

    @SmallTest
    public void testValidateCardCvc()
    {
        // test good cvc
        assertTrue(Utils.validateCardCvc("123", Card.Type.MASTERCARD));

        // test bad cvc
        assertFalse(Utils.validateCardCvc("123", Card.Type.AMERICAN_EXPRESS));
    }
}
