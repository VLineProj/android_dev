package com.example.stone.test.MessageList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.stone.test.R;
import com.example.stone.test.model.User;
import com.example.stone.test.util.userHelper;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;

/**
 * Created by 润 on 2016/4/9.
 */
public class AddContent extends Activity{
    private TextView titleView=null;
    private EditText editText=null;
    private ImageButton cancel,confirm;
    private String str;
    private String msgid="";
    private String codeid="";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontent);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        msgid=bundle.getString("msgid");
        codeid=bundle.getString("codeid");
        str=bundle.getString("title");
        System.out.println(str);
        titleView=(TextView)findViewById(R.id.textview_title);
        titleView.setText(str);
        editText=(EditText)findViewById(R.id.content);
        String msg=str+editText.getText().toString();           //整个内容

        cancel= (ImageButton) findViewById(R.id.button_back);
        confirm= (ImageButton) findViewById(R.id.button_confirm);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=null;
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=editText.getText().toString();
                String url=sendContentUrl(content);

                Handler handler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        String result= (String) msg.obj;
                        if (result.contains("true")){
                            finish();
                        }else{
                            Toast.makeText(AddContent.this, "提交失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                send(url,handler);
            }
        });
    }


    public void tijiao(String  getPeopleInfo) {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String result = (String) msg.obj;
                if(result.equals("true")){
                    if(str.equals("")){
                        str=null;
                    }else{
                        //String atUrl=getAtUrl(msgContent,redUser,userName);
                        //sendAt(atUrl);
                        str=null;
                    }
                    Intent intentAdd=new Intent();
                    intentAdd.setClass(AddContent.this, MsgListActivity.class);
                    startActivity(intentAdd);
                }else {
                    Toast.makeText(AddContent.this,"网络不给力，稍后再试",Toast.LENGTH_LONG);
                }
            }
        };
        new Thread(new AccessNetwork("GET",getPeopleInfo,handler)).start();
    }

    public void send(String  getPeopleInfo,Handler handler) {
        new Thread(new AccessNetwork("GET",getPeopleInfo,handler)).start();
    }

    //简单的发送消息，生成json
    public String sendContentUrl(String msgContent){
        try{
            User user=userHelper.getLocalUser(this);
            JSONObject para = new JSONObject();
            para.put("codeId",codeid);
            para.put("msgContent",msgContent);
            para.put("userName",user.getUserName());
            para.put("userPasswdHash",user.getPasswordMD5());
            para.put("redMsgId",msgid);
            String text= URLEncoder.encode(para.toString());
            String getPeopleInfo="http://vline.zhengzi.me/control.php?func=msgSend"+"&para="+text;
            return getPeopleInfo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
