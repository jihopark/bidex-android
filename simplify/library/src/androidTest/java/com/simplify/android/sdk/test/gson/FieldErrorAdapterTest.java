package com.simplify.android.sdk.test.gson;


import android.test.suitebuilder.annotation.SmallTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simplify.android.sdk.model.FieldError;
import com.simplify.android.sdk.gson.FieldErrorAdapter;

import junit.framework.TestCase;

public class FieldErrorAdapterTest extends TestCase
{
    private Gson mGson;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        mGson = new GsonBuilder()
                .registerTypeAdapter(FieldError.class, new FieldErrorAdapter())
                .create();
    }

    @SmallTest
    public void testDeserializeFromJson()
    {
        // init sample field error json
        String json = "{\"field\":\"some field\",\"code\":\"some code\",\"message\":\"some message\"}";

        // deserialize int field error
        FieldError error = mGson.fromJson(json, FieldError.class);

        // test all parameters were correctly set
        assertNotNull(error);
        assertEquals(error.getField(), "some field");
        assertEquals(error.getCode(), "some code");
        assertEquals(error.getMessage(), "some message");
    }
}
