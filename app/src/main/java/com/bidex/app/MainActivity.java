package com.bidex.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView _commentListView;
    private CommentListAdapter commentListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _commentListView = (ListView) findViewById(R.id.comments_list);
        commentListAdapter = new CommentListAdapter(MainActivity.this, getSampleComments());
        _commentListView.setAdapter(commentListAdapter);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
