package com.example.stone.test.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.stone.test.MainActivity;
import com.example.stone.test.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by stone on 4/9/16.
 */
public class userHelper {
    public static String getUserURL="http://vline.zhengzi.me/control.php?func=userFalseReg";
    private static String getUserMsgURL="http://vline.zhengzi.me/control.php?func=getUserMsgs&para=";
    private static String modifyNicknameURL="http://vline.zhengzi.me/control.php?func=changeUserNickName&para=";

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

    public static void removeUser(Context context){
        SharedPreferences.Editor editor=context.getSharedPreferences("vline",Context.MODE_PRIVATE).edit();
        editor.remove("username");
        editor.remove("pwmd5");
        editor.commit();
    }

    public static void getNetUser(Handler handler){
        new Thread(new AccessNetwork("GET",userHelper.getUserURL,handler)).start();
    }

    public static void getUserMsg(User user,Handler handler){
        try {
            JSONObject json = new JSONObject();
            json.put("userName", user.getUserName());
            json.put("userPasswdHash", user.getPasswordMD5());

            String url=getUserMsgURL+json.toString();

            new Thread(new AccessNetwork("GET", url, handler)).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static User parseUserJson(String json){
        User user=new User();
        try {
            JSONObject objects=new JSONObject(json);
            user.setUserName(objects.getString("userName"));
            user.setPasswordMD5(objects.getString("userPasswdHash"));
            user.setAvator(objects.getString("userAvatar"));
            user.setNickname(objects.getString("userNickName"));
        } catch (JSONException e) {
            Toast.makeText(null, json, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return user;
    }

    public static void modifyNickname(User user,Handler handler) {
        try {
            JSONObject json = new JSONObject();
            json.put("userName", user.getUserName());
            json.put("userPasswdHash", user.getPasswordMD5());
            json.put("nickName", user.getNickname());

            String url=modifyNicknameURL+ URLEncoder.encode(json.toString(), "UTF-8");

            new Thread(new AccessNetwork("GET", url, handler)).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
