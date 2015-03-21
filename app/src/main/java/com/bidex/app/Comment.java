package com.bidex.app;

/**
 * Created by parkjiho on 3/21/15.
 */
public class Comment {
    private String _userId;
    private int _userBid;
    private String _detail;

    public Comment(String userId, int userBid, String detail){
        _userId = userId;
        _userBid = userBid;
        _detail = detail;
    }

    public String getDetail(){ return _detail; }
    public String getUserId(){
        return _userId;
    }
    public int getUserBid(){
        return _userBid;
    }
}
