package com.bidex.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private ParseObject deal;

    private RelativeLayout bidButton;
    private ListView _commentListView;
    private CommentListAdapter commentListAdapter;
    private TextView dealTitle,totalBidder,topBidNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _commentListView = (ListView) findViewById(R.id.comments_list);
        commentListAdapter = new CommentListAdapter(MainActivity.this, getSampleComments());
        _commentListView.setAdapter(commentListAdapter);
        bidButton = (RelativeLayout) findViewById(R.id.bid_button);

        dealTitle = (TextView) findViewById(R.id.deal_title);
        totalBidder = (TextView) findViewById(R.id.total_bidder_number);
        topBidNumber = (TextView) findViewById(R.id.top_bid_number);
        setBidButton();
       // setTimer(null);

        setDB();
    }

    private void setDB(){

        Log.d("MainActivity/setDB","Set DB");
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "8y2Ma1EO394oPIoyEqVIBXFhtBmGjMQYjXfDm9od", "ftK0rwjfMgai0KxoXBvsbr9GYgr952Kq8TndWi0o");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Deal");
        query.getInBackground("z17eDDD5bc", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Log.d("MainActivity/done","parse "+ object.getString("title"));
                    deal = object;
                    setTimer(deal.getDate("dealEndTime"));
                    dealTitle.setText(object.getString("title"));
                    setUpBid();
                } else {
                    // something went wrong
                }
            }
        });
    }

    private void setUpBid(){
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Bid");

        query2.whereEqualTo("bid_for", deal);
        query2.addDescendingOrder("amount");
        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> bidList, ParseException e) {
                if (e == null) {
                    Log.d("MainActivity/done", "Total Bidder " + bidList.size());
                    totalBidder.setText(""+bidList.size());
                    topBidNumber.setText("$" + bidList.get(0).getInt("amount"));

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

    }

    private void setUpInitialBids(ParseObject deal){
        ParseObject bid = new ParseObject("Bid");
        bid.put("amount",100);
        bid.put("bidTime", new Date(Calendar.getInstance().getTimeInMillis()));
        bid.put("bid_for",deal);

        ParseObject bid2 = new ParseObject("Bid");
        bid2.put("amount",150);
        bid2.put("bidTime", new Date(Calendar.getInstance().getTimeInMillis()));
        bid2.put("bid_for",deal);
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
        if (id==R.id.action_login){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
