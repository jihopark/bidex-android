package com.simplify.android.sdk.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.simplify.android.sdk.Constants;
import com.simplify.android.sdk.Simplify;
import com.simplify.android.sdk.model.Card;

import junit.framework.TestCase;

import java.security.InvalidParameterException;


public class SimplifyTest extends TestCase
{
    private Simplify mSimplify;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        mSimplify = new Simplify("sbpb_1234567890");
    }

    @SmallTest
    public void testCorrectUrlInterprettedFromApiKey()
    {
        // test default of sandbox url
        assertEquals(mSimplify.getUrl(), Constants.API_BASE_SANDBOX_URL);

        // test live url
        mSimplify.setKey("lvpb_1234567890");
        assertEquals(mSimplify.getUrl(), Constants.API_BASE_LIVE_URL);

        // test invalid key format
        try {
            mSimplify.setKey("bogus");
            fail("Should have thrown exception on unrecognized key prefix");
        }
        catch (InvalidParameterException e) {
        }
    }

    @SmallTest
    public void testCreateCard()
    {
        // init card object
        Card card = mSimplify.createCard("5105105105105100", "01", "50", "123");

        // test card is appropriately populated
        assertNotNull(card);
        assertEquals(card.getNumber(), "5105105105105100");
        assertEquals(card.getMonth(), 1);
        assertEquals(card.getYear(), 2050);
        assertEquals(card.getCvc(), "123");
    }
}
