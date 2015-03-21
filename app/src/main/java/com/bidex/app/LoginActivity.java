package com.bidex.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by parkjiho on 3/21/15.
 */
public class LoginActivity extends ActionBarActivity {

    private Button _signUp, _login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _signUp = (Button) findViewById(R.id.signup_button);
        _login = (Button) findViewById(R.id.login_button);
        _signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignActivity.class);
                startActivity(intent);
            }
        });
    }
}