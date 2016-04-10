package com.example.stone.test.util;

import android.os.Handler;
import android.util.JsonReader;

import com.example.stone.test.model.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stone on 4/9/16.
 */
public class imgHelper {


//    public static ArrayList<String> getAllAvator(){
//        String urlString="http://vline.zhengzi.me/control.php?func=getAllBuildInAvatars";
//        String result=netHelper.getURLResponse(urlString);
//
//        return paraseAvatorJson(result);
//    }


    private static ArrayList<String> paraseAvatorJson(String json){
        ArrayList<String> avators= new ArrayList<>();

        try {
            JSONObject objects=new JSONObject(json);
            for (int i=1;i<10;i++){
                avators.add(objects.getString(String.valueOf(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return avators;
    }

    public static void changeAvatar(User user,int id,Handler handler){
        try {
            JSONObject json = new JSONObject();
            json.put("userName", user.getUserName());
            json.put("userPasswdHash", user.getPasswordMD5());
            json.put("avatarId", id);

            String url = "http://vline.zhengzi.me/control.php?func=changeUserAvatar&para="+json.toString();
            new Thread(new AccessNetwork("GET",url,handler)).start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
