package com.example.stone.test;

import com.example.stone.test.model.User;
import com.example.stone.test.util.userHelper;

import org.junit.Test;

/**
 * Created by stone on 4/9/16.
 */
public class netTest {
    @Test
    public void testJson(){
        User user=userHelper.getNetUser();
        System.out.println(user.toString());
    }
}
