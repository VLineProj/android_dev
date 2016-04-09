package com.example.stone.test.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.stone.test.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by stone on 4/9/16.
 */
public class userHelper {

    public static User getLocalUser(Context context){
        User user=new User();
        SharedPreferences sp=context.getSharedPreferences("vline", Context.MODE_PRIVATE);
        user.setUserName(sp.getString("username",null));
        user.setPasswordMD5(sp.getString("pwmd5",null));
        user.setAvator(sp.getString("avator",null));
        user.setNickname(sp.getString("nickname",null));

        return user;
    }

    public static void saveLocalUser(Context context,User user){
        SharedPreferences.Editor editor=context.getSharedPreferences("vline",Context.MODE_PRIVATE).edit();
        editor.putString("username",user.getUserName());
        editor.putString("pwmd5", user.getPasswordMD5());
        editor.putString("avator", user.getAvator());
        editor.putString("nickname", user.getNickname());

        editor.commit();
    }

    public static User getNetUser(){
        String urlString="http://vline.zhengzi.me/control.php?func=userFalseReg";
        String result=netHelper.getURLResponse(urlString);
        Gson gson=new Gson();
        User user=gson.fromJson(result,User.class);
        return user;
    }
}
