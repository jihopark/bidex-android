package com.simplify.android.sdk.test.model;


import android.test.suitebuilder.annotation.SmallTest;

import com.simplify.android.sdk.model.Card;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.HashMap;

public class CardTest extends TestCase
{
    private Card mCard;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        mCard = new Card();
    }

    @SmallTest
    public void testCardNumbersAreFormattedCorrectly()
    {
        // test set number/unknown type
        mCard.setNumber("1234567890");
        assertEquals(mCard.getNumber(), "1234567890");
        assertEquals(mCard.getType(), Card.Type.UNKNOWN);

        // map out test formats
        HashMap<Card.Type, String[]> numbers = new HashMap<Card.Type, String[]>();
        numbers.put(Card.Type.AMERICAN_EXPRESS, new String[] {
                "371449635398431",
                "3714 496353 98431"
        });
        numbers.put(Card.Type.DINERS, new String[] {
                "30569309025904",
                "3056 9309 0259 04"
        });
        numbers.put(Card.Type.DISCOVER, new String[] {
                "6011000990139424",
                "6011 0009 9013 9424"
        });
        numbers.put(Card.Type.JCB, new String[] {
                "3530111333300000",
                "3530 1113 3330 0000"
        });
        numbers.put(Card.Type.MASTERCARD, new String[] {
                "5105105105105100",
                "5105 1051 0510 5100"
        });
        numbers.put(Card.Type.VISA, new String[] {
                "4111111111111111",
                "4111 1111 1111 1111"
        });

        // test each formatting
        for(Card.Type type : Card.Type.values()) {
            if (type != Card.Type.UNKNOWN) {
                mCard.setNumber(numbers.get(type)[0]);

                assertEquals(type.format(mCard.getNumber()), numbers.get(type)[1]);
                assertEquals(mCard.getType(), type);
            }
        }
    }

    @SmallTest
    public void testTypeSetsAndParsesCorrectly()
    {
        // test basic set
        mCard.setType(Card.Type.MASTERCARD);
        assertEquals(mCard.getType(), Card.Type.MASTERCARD);

        // test parsing good string type
        mCard.setType("MASTERCARD");
        assertEquals(mCard.getType(), Card.Type.MASTERCARD);

        // test parsing bad string type
        mCard.setType("bogus");
        assertEquals(mCard.getType(), Card.Type.UNKNOWN);
    }

    @SmallTest
    public void testExpirationSetterFormatsCorrectly()
    {
        mCard.setMonth(1);
        assertEquals(mCard.getMonth(), 1);

        mCard.setMonth("04");
        assertEquals(mCard.getMonth(), 4);

        mCard.setYear(2026);
        assertEquals(mCard.getYear(), 2026);

        mCard.setYear(26);
        assertEquals(mCard.getYear(), 2026);

        mCard.setMonth(2);
        mCard.setYear(25);
        assertEquals(mCard.getExpiration(), "02/25");
    }

    @SmallTest
    public void testCardDataValidity()
    {
        // test good number
        mCard.setNumber("5105105105105100");
        assertTrue(mCard.isValidNumber());

        // test bad number
        mCard.setNumber("5105105105105");
        assertFalse(mCard.isValidNumber());

        // get current year
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);

        // test good date
        mCard.setMonth(1);
        mCard.setYear(year + 1);
        assertTrue(mCard.isValidExpiration());

        // test bad date
        mCard.setYear(year - 1);
        assertFalse(mCard.isValidExpiration());

        // test incomplete date
        mCard.setMonth(0);
        mCard.setYear(0);
        assertFalse(mCard.isValidExpiration());

        // test good cvc
        mCard.setType(Card.Type.MASTERCARD);
        mCard.setCvc("123");
        assertTrue(mCard.isValidCvc());

        // test bad cvc
        mCard.setCvc("1234");
        assertFalse(mCard.isValidCvc());

        // test good everything
        mCard.setNumber("5105105105105100");
        mCard.setMonth(1);
        mCard.setYear(year + 1);
        mCard.setCvc("123");
        assertTrue(mCard.isValid());
    }
}
