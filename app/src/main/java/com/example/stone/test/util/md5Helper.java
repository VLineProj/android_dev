package com.example.stone.test.util;

import android.os.Handler;

import com.example.stone.test.model.User;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by stone on 4/9/16.
 */
public class md5Helper {

    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest md5=MessageDigest.getInstance("md5Helper");
        md5.update(val.getBytes());
        byte[] mi=md5.digest();
        return getString(mi);
    }

    private static String getString(byte[] b){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < b.length; i ++){
            sb.append(b[i]);
        }
        return sb.toString();
    }

    public static void getCodeID(User user, String code,Handler handler){
        try {
            String url = "http://vline.zhengzi.me/control.php?func=scanCode&para=";
            JSONObject json = new JSONObject();
            json.put("userName",user.getUserName());
            json.put("userPasswdHash",user.getPasswordMD5());
            json.put("codeContent", code);
            String param= URLEncoder.encode(json.toString(),"UTF-8");

            new Thread(new AccessNetwork("GET", url+param,handler)).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
