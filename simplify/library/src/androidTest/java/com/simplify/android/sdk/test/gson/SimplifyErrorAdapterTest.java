package com.simplify.android.sdk.test.gson;


import android.test.suitebuilder.annotation.SmallTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simplify.android.sdk.model.FieldError;
import com.simplify.android.sdk.model.SimplifyError;
import com.simplify.android.sdk.gson.FieldErrorAdapter;
import com.simplify.android.sdk.gson.SimplifyErrorAdapter;

import junit.framework.TestCase;

public class SimplifyErrorAdapterTest extends TestCase
{
    private Gson mGson;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        mGson = new GsonBuilder()
                .registerTypeAdapter(SimplifyError.class, new SimplifyErrorAdapter())
                .registerTypeAdapter(FieldError.class, new FieldErrorAdapter())
                .create();
    }

    @SmallTest
    public void testDeserialize()
    {
        // init sample error json
        String json = "{\"error\":{\"code\":\"some code\",\"message\":\"some message\",\"fieldErrors\":[{\"field\":\"some field\",\"code\":\"some code\",\"message\":\"some message\"}]},\"reference\":\"some reference\"}";

        // deserialize into error
        SimplifyError error = mGson.fromJson(json, SimplifyError.class);

        // test all parameters were correctly set
        assertNotNull(error);
        assertEquals(error.getCode(), "some code");
        assertEquals(error.getMessage(), "some message");
        assertEquals(error.getReference(), "some reference");
        assertEquals(error.getFieldErrors()[0].getField(), "some field");
        assertEquals(error.getFieldErrors()[0].getCode(), "some code");
        assertEquals(error.getFieldErrors()[0].getMessage(), "some message");
    }
}
