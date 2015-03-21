package com.bidex.app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by parkjiho on 3/21/15.
 */
public class CommentView extends LinearLayout {

    private TextView userId, userBid, commentDetail;
    private Comment _comment;

    public CommentView(Context context, Comment comment) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout layout =(LinearLayout) inflater.inflate(R.layout.item_comment, this, true);
        userId = (TextView) layout.findViewById(R.id.user_id);
        userBid = (TextView) layout.findViewById(R.id.user_bid);
        commentDetail = (TextView) layout.findViewById(R.id.comment_detail);
        setComment(comment);
    }

    public void setComment(Comment comment){
        _comment = comment;

        userId.setText("@"+_comment.getUserId());
        if (_comment.getUserBid() > 0)
            userBid.setText("$"+_comment.getUserBid());
        else
            userBid.setText("");
        commentDetail.setText(_comment.getDetail());
        Log.d("CommentView/setComment", "Setting comments for " + comment.getUserId());
    }
}
