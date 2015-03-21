package com.bidex.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.simplify.android.sdk.Simplify;
import com.simplify.android.sdk.model.Card;
import com.simplify.android.sdk.model.SimplifyError;
import com.simplify.android.sdk.model.Token;
import com.simplify.android.sdk.view.CardEditor;

/**
 * Created by parkjiho on 3/21/15.
 */
public class PaymentActivity extends ActionBarActivity {

    private CardEditor cardEditor;
    private Simplify simplify;

    private Simplify.CreateTokenListener listener = new Simplify.CreateTokenListener()
    {
        @Override
        public void onSuccess(Token token)
        {
            Log.i("PaymentActivity/onSuccess", "Created Token: " + token.getId());
            processTokenToBackEnd(token.getId());
        }

        @Override
        public void onError(SimplifyError error)
        {
            cardEditor.showErrorOverlay("Error");
            Log.e("PaymentActivity/onError", "Error Creating Token: " + error.getMessage());
        }
    };

    private void processTokenToBackEnd(String token){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        simplify = new Simplify(getResources().getString(R.string.public_key));
        cardEditor = (CardEditor) findViewById(R.id.card_editor);
        cardEditor.setAmount(1000);

        cardEditor.setOnChargeClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Card card = cardEditor.getCard();
                AsyncTask<?,?,?> createTokenTask = simplify.createCardToken(card, listener);
            }
        });
    }
}