package com.example.stone.test.MessageList;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by stone on 4/9/16.
 */
public class AccessNetwork implements Runnable {
    private String op;
    private String url;
    private Handler h;

    public AccessNetwork(String op, String url, Handler h) {
        super();
        this.op = op;
        this.url = url;
        this.h = h;
    }

    @Override
    public void run() {
        Message m = new Message();
        m.what = 0x123;
        if (op.equals("GET")) {
            Log.i("iiiiiii", "发送GET请求");
            m.obj = getURLResponse(url);
            Log.i("iiiiiii", ">>>>>>>>>>>>" + m.obj);
        }
        if (op.equals("POST")) {
            Log.i("iiiiiii", "发送POST请求");
            m.obj = getURLResponse(url);
            Log.i("gggggggg", ">>>>>>>>>>>>" + m.obj);
        }
        h.sendMessage(m);
    }

    public String getURLResponse(String urlName) {
        String result = "";
        BufferedReader in = null;
        try {

            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
