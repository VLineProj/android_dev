package com.example.stone.test.util;

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
}
