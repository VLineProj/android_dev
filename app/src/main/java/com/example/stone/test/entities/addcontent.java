package com.example.stone.test.entities;

/**
 * Created by 润 on 2016/4/9.
 */
public class addcontent {
    //send:二维码ID、用户名、内容、坐标、位置
    //返回true,重新获取
    private String codeId;             //reMsg
    private String userName;
    private String msgContent;
    private String msgCorrdinate;
    private String msgLocation;

    //msgId（内容Id）、被@的人，我自己name，二次发送内容
    public String getCodeId(){
        return codeId;
    }
    public void setCodeId(String codeId){
        this.codeId=codeId;
    }

    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgCorrdinate() {
        return msgCorrdinate;
    }

    public void setMsgCorrdinate(String msgCorrdinate) {
        this.msgCorrdinate = msgCorrdinate;
    }

    public String getMsgLocation() {
        return msgLocation;
    }

    public void setMsgLocation(String msgLocation) {
        this.msgLocation = msgLocation;
    }
}
