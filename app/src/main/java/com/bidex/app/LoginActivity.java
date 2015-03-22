package com.bidex.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by parkjiho on 3/21/15.
 */
public class LoginActivity extends ActionBarActivity {

    private String id,pw;
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

        _login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = ((EditText)findViewById(R.id.LoginID)).getText().toString();
                pw = ((EditText)findViewById(R.id.LoginPW)).getText().toString();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                query.whereEqualTo("userid",id);
                query.whereEqualTo("pw",pw);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        if (parseObjects.size()!=0){
                            MainActivity.currentUser = parseObjects.get(0);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                        else
                            Log.d("LoginActivity/findInBackground","Error running");
                    }
                });


            }
        });
    }
}