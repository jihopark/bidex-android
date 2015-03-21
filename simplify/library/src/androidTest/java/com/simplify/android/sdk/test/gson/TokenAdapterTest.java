package com.simplify.android.sdk.test.gson;

import android.test.suitebuilder.annotation.SmallTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simplify.android.sdk.model.Card;
import com.simplify.android.sdk.model.Token;
import com.simplify.android.sdk.gson.CardAdapter;
import com.simplify.android.sdk.gson.TokenAdapter;

import junit.framework.TestCase;


public class TokenAdapterTest extends TestCase
{
    private Gson mGson;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        mGson = new GsonBuilder()
                .registerTypeAdapter(Token.class, new TokenAdapter())
                .registerTypeAdapter(Card.class, new CardAdapter())
                .create();
    }

    @SmallTest
    public void testDeserialize()
    {
        // init sample token json
        String json = "{\"card\":{\"id\":\"some id\",\"type\":\"MASTERCARD\",\"last4\":\"1234\",\"expMonth\":1,\"expYear\":50,\"dateCreated\":1234567890},\"used\":false,\"id\":\"some id\"}";

        // deserialize into token
        Token token = mGson.fromJson(json, Token.class);

        // test all parameters were correctly set
        assertNotNull(token);
        assertNotNull(token.getCard());
        assertEquals(token.getId(), "some id");
        assertFalse(token.isUsed());
        assertEquals(token.getCard().getId(), "some id");
        assertEquals(token.getCard().getType(), Card.Type.MASTERCARD);
        assertEquals(token.getCard().getLast4(), "1234");
        assertEquals(token.getCard().getMonth(), 1);
        assertEquals(token.getCard().getYear(), 2050);
        assertEquals(token.getCard().getDateCreated(), 1234567890L);
    }
}
