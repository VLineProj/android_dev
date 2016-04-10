package com.example.stone.test.entities;

/**
 * Created by 润 on 2016/4/9.
 */
public class People_information {
    private String drawUrl;    //func=getCodeMsgs返回
    private String Nickname;
    private String time;
    private String description;
    private String msgId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getDrawUrl(){          //图片的Url
        return drawUrl;
    }

    public void setDrawUrl(String drawUrl){
        this.drawUrl=drawUrl;
    }

    public String getNickname() {
        return Nickname;
    }
    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time=time;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
