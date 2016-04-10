package com.example.stone.test.entities;

/**
 * Created by 润 on 2016/4/9.
 */
public class AtContent {
    //msgId（内容Id）、被@的人，我自己name，二次发送内容
    private String msgContent;    //func=msgSend
    private String redUser;
    private String userName;

    public String getMsgContent(){
        return msgContent;
    }

    public void setMsgContent(String msgContent){
        this.msgContent=msgContent;
    }

    public String getRedUser(){
        return redUser;
    }

    public void setRedUser(String redUser){
        this.redUser=redUser;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName=userName;
    }
}
