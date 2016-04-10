package com.example.stone.test.entities;

/**
 * Created by 润 on 2016/4/9.
 */
public class location_info {
    private String User;    //func=getCodeMsgs
    private String Hash;
    private String codeType;
    private String codeContent;

    public String getUser(){
        return User;
    }
    public void setUser(String User){
        this.User=User;
    }
    public String getHash(){
        return Hash;
    }
    public void setHash(String Hash){
        this.Hash=Hash;
    }
    public String getCodeType(){
        return codeType;
    }
    public void setCodeType(String codeType){
        this.codeType=codeType;
    }
    public String getCodeContent(){
        return codeContent;
    }
    public void setCodeContent(String codeContent){
        this.codeContent=codeContent;
    }
}
