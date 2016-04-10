package com.example.stone.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stone.test.MessageList.MsgListActivity;
import com.example.stone.test.image.CircularImage;
import com.example.stone.test.location.location;
import com.example.stone.test.model.User;
import com.example.stone.test.util.userHelper;
import com.example.stone.test.util.md5Helper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {

    private final static int SCANNIN_GREQUEST_CODE = 1;
    private User user = null;
    private CircularImage cover_user_photo;
    private TextView nickname;
    private EditText nickname_edit;
    private Button edit_btn;
    private String codemd5;
    private String codeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //设置头像
        cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);
        cover_user_photo.setImageResource(R.drawable.my_head);
        cover_user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, avatorActivity.class);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });
        nickname = (TextView) findViewById(R.id.nickname);
        nickname_edit = (EditText) findViewById(R.id.nickname_edit);
        edit_btn = (Button) findViewById(R.id.nickname_edit_btn);
        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname_edit.setText(nickname.getText());
                nickname_edit.setVisibility(View.VISIBLE);
                edit_btn.setVisibility(View.VISIBLE);
                nickname.setVisibility(View.INVISIBLE);
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname.setVisibility(View.VISIBLE);
                edit_btn.setVisibility(View.GONE);
                nickname_edit.setVisibility(View.GONE);
                user.setNickname(nickname_edit.getText().toString());
                userHelper.modifyNickname(user, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        getUserMsg();
                    }
                });
            }
        });

        //设置进入世界按钮
        Button btn = (Button) findViewById(R.id.btn_goin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });

        getUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCANNIN_GREQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Bundle bundle = data.getBundleExtra("bundle");
                String code = bundle.getString("result");
                Handler handler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        String result= (String) msg.obj;
                        try {
                            JSONObject json=new JSONObject(result);
                            codemd5=json.getString("codeId");
                            codeType=json.getString("queryType");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Intent intent=new Intent();
                        intent.setClass(MainActivity.this,MsgListActivity.class);
                        intent.putExtra("codeid", codemd5);
                        intent.putExtra("codeType",codeType);
                        startActivity(intent);
                    }
                };
                md5Helper.getCodeID(user,code,handler);
            }
        }
    }

    private void init() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserMsg();
    }

    public void getUserMsg() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String result = (String) msg.obj;
                user = userHelper.parseUserJson(result);
                setUser();
                userHelper.saveLocalUser(MainActivity.this,user);
            }
        };
        userHelper.getUserMsg(user, handler);
    }


    public void getUser() {

        user = userHelper.getLocalUser(this);
        if (user.getUserName() == null || user.getPasswordMD5() == null) {
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    String result = (String) msg.obj;
                    user = userHelper.parseUserJson(result);
                    userHelper.saveLocalUser(MainActivity.this, user);
                    setUser();
                }
            };
            userHelper.getNetUser(handler);
        } else {
            getUserMsg();
        }
    }

    public void setUser() {
        // 显示nickname
        nickname.setText(user.getNickname());

        //显示用户头像
        ImageLoader imageloader = ImageLoader.getInstance();
        imageloader.displayImage(user.getAvator(), cover_user_photo);
    }
}
