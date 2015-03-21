package com.bidex.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private RelativeLayout bidButton;
    private ListView _commentListView;
    private CommentListAdapter commentListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _commentListView = (ListView) findViewById(R.id.comments_list);
        commentListAdapter = new CommentListAdapter(MainActivity.this, getSampleComments());
        _commentListView.setAdapter(commentListAdapter);
        bidButton = (RelativeLayout) findViewById(R.id.bid_button);

        setBidButton();
        setTimer(null);
    }

    private void setBidButton(){
        final DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        };

        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.bid_confirm_title)
                        .setMessage(R.string.bid_confirm_message)
                        .setPositiveButton(R.string.bid_confirm_yes, positiveListener)
                        .setNegativeButton(R.string.bid_confirm_no, null);
                builder.create().show();
            }
        });
    }

    private void setTimer(Date date){
        if (date==null){
            date = new Date(Calendar.getInstance().getTimeInMillis() + 1000*60*60);
        }
        final Date fDate = date;
        final TextView timeLeft = (TextView) findViewById(R.id.time_left_number);
        long timeLeftDate = fDate.getTime() - Calendar.getInstance().getTimeInMillis();
        new CountDownTimer(timeLeftDate, 1000) {
            public void onTick(long millisUntilFinished) {

                long timeLeftDate = fDate.getTime() - Calendar.getInstance().getTimeInMillis();

                timeLeft.setText((timeLeftDate / (60 * 60 * 1000) % 24) + ":" + (timeLeftDate/(60 * 1000) % 60) + ":" + (timeLeftDate/1000%60));
            }

            public void onFinish() {
                timeLeft.setText("ENDED");
            }
        }
        .start();
    }

    private List<Comment> getSampleComments(){
        ArrayList<Comment> list = new ArrayList<Comment>();
        list.add(new Comment("bidder123",2300,"I am getting this deal"));
        list.add(new Comment("bidder4",0,"Hey this is cool"));
        list.add(new Comment("bidder3",100,"Hello world"));
        list.add(new Comment("bidder2",0,"yeah this is quite awesome"));
        list.add(new Comment("first1",50,"First"));
        return list;
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
