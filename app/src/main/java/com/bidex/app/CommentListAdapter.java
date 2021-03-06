package com.bidex.app;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.LinkedList;

/**
 * Created by parkjiho on 3/21/15.
 */
public class CommentListAdapter extends BaseAdapter {
    private LinkedList<Comment> comments;
    private Context _context;

    public CommentListAdapter(Context context, LinkedList<Comment> list){
        comments = list;
        _context = context;
    }

    public void addItem(Comment comment){
        comments.addFirst(comment);
        notifyDataSetChanged();
    }

    public int getCount() {
        return comments.size();
    }

    public long getItemId(int i){
        return comments.get(i).getUserBid();
    }
    public Comment getItem(int position){
        return comments.get(position);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentView view;
        if (convertView == null){
            view = new CommentView(_context, getItem(position));
            Log.d("CommentListAdapter/getView","Created new View");
        }
        else{
            view = (CommentView) convertView;
            ((CommentView)convertView).setComment(getItem(position));
        }
        if (position % 2 == 0){
            view.setBackgroundColor(_context.getResources().getColor(R.color.col1));
        }
        else
            view.setBackgroundColor(_context.getResources().getColor(R.color.col2));
        return view;
    }
}
