package com.simplify.android.sdk.test.gson;


import android.test.suitebuilder.annotation.SmallTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simplify.android.sdk.model.Card;
import com.simplify.android.sdk.gson.CardAdapter;

import junit.framework.TestCase;

public class CardAdapterTest extends TestCase
{
    private Gson mGson;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        mGson = new GsonBuilder()
                .registerTypeAdapter(Card.class, new CardAdapter())
                .create();
    }

    @SmallTest
    public void testSerializeToJsonWithCorrectParameters()
    {
        // init card object
        Card card = new Card()
            .setNumber("5105105105105100")
            .setMonth(1)
            .setYear(2050)
            .setCvc("123");

        JsonElement elem = mGson.toJsonTree(card);
        JsonObject json = elem.getAsJsonObject();

        // test card object serialized with correct json parameters
        assertEquals(json.get("number").getAsString(), "5105105105105100");
        assertEquals(json.get("expMonth").getAsInt(), 1);
        assertEquals(json.get("expYear").getAsInt(), 50);
        assertEquals(json.get("cvc").getAsString(), "123");
    }

    @SmallTest
    public void testDeserializeFromJson()
    {
        // init sample card json
        String json = "{\"id\":\"some id\",\"type\":\"MASTERCARD\",\"last4\":\"1234\",\"expMonth\":1,\"expYear\":50,\"dateCreated\":1234567890}";

        // deserialize into card
        Card card = mGson.fromJson(json, Card.class);

        // test all parameters were correctly set
        assertNotNull(card);
        assertEquals(card.getId(), "some id");
        assertEquals(card.getType(), Card.Type.MASTERCARD);
        assertEquals(card.getLast4(), "1234");
        assertEquals(card.getMonth(), 1);
        assertEquals(card.getYear(), 2050);
        assertEquals(card.getDateCreated(), 1234567890L);
    }
}
