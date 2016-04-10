package com.example.stone.test.MessageList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.DialogPreference;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.stone.test.R;
import com.example.stone.test.entities.People_information;
import com.example.stone.test.model.User;
import com.example.stone.test.util.userHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 润 on 2016/4/9.
 */
public class MsgListActivity extends Activity{

    private String getInfoUrl;
    private String codeId="";
    private ImageButton button_add;
    private String url="http://vline.zhengzi.me/static/avatar/1.jpg";
    private int id[]={R.drawable.pic1, R.drawable.pic1,R.drawable.pic1,R.drawable.pic1
            ,R.drawable.pic1,R.drawable.pic1,R.drawable.pic1,R.drawable.pic1,R.drawable.pic1};
    private String nickName[]={"宁静","白衣未央","苏染","浮生物语","沫去丶","要想成功必须强大","你是病毒我却不忍用360",
            "超级无敌噼里啪啦大boss","止不住那流逝的年华"};
    private String time[]={"2016","2016","2016","2016","沫去丶","要想成功必须强大","你是病毒我却不忍用360",
            "超级无敌噼里啪啦大boss","止不住那流逝的年华"};
    private String description[]={"我的人生只是一道直线，转弯就是因为想遇见你","时间，让深的东西越来越深，让浅的东西越来越浅。","弱水三千，我只取一勺，可是到最后我悲惨的发现我的居然是漏勺。",
            "口上说着释然的人最终心里会疼痛到不能言语","我多想带你去看看以前还没爱上你的我","知道雪为什么是白色吗 因为它忘记了自己曾经的颜色",
            "好想轰轰烈烈爱她一遍,好想平平淡淡陪他一生","Say bye bye to my love ！","只有在他无聊没人玩寂寞空虚的时候才会过来和你说几句话"
    };
    private ListView lv_view;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        codeId=bundle.getString("codeid");

        setContentView(R.layout.view_listview);
        //添加内容
        User user= userHelper.getLocalUser(MsgListActivity.this);
        getInfoUrl=getPeopleInfo(user.getUserName(),user.getPasswordMD5(),codeId);
        init(getInfoUrl);
        button_add= (ImageButton) findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd=new Intent();
                intentAdd.setClass(MsgListActivity.this, AddContent.class);
                intentAdd.putExtra("title", "");
                intentAdd.putExtra("codeid",codeId);
                startActivity(intentAdd);
            }
        });

        ImageButton btn_back= (ImageButton) findViewById(R.id.button_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgListActivity.this.finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        init(getInfoUrl);
    }

    private List<People_information> getList(Reader response) throws IOException{
        List<People_information> list = new ArrayList<People_information>();
        try {
            JsonReader reader = new JsonReader(response);
            reader.beginArray();
            while (reader.hasNext()) {
                Log.e("stone", "开始解析");
                reader.beginObject();
                People_information people = new People_information();
                while (reader.hasNext()) {
                    String jsonStr = reader.nextName();
                    if ("userNickName".equals(jsonStr)) {
                        people.setNickname(reader.nextString());
                    } else if ("msgId".equals(jsonStr)) {
                        people.setMsgId(reader.nextString());
                    } else if ("msgContent".equals(jsonStr)) {
                        people.setDescription(reader.nextString());
                    } else if ("msgTime".equals(jsonStr)) {
                        people.setTime(reader.nextString());
                    } else if ("userAvatar".equals(jsonStr)) {
                        people.setDrawUrl(reader.nextString());
                    }
                }
                list.add(people);
                Log.e("stone", people.getMsgId());

                reader.endObject();
            }
            reader.endArray();
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(MsgListActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
        }
        return list;
    }


    //生成json,发送数据
    public String getPeopleInfo(String userName,String userPasswdHash,String codeId){
    try{
        JSONObject para = new JSONObject();
        para.put("userName",userName);
        para.put("userPasswdHash",userPasswdHash);
        para.put("codeId",codeId);
        String getPeopleInfo="http://vline.zhengzi.me/control.php?func=getCodeMsgs"+"&para="+para.toString();
        return getPeopleInfo;
    }catch (Exception e){
        e.printStackTrace();
    }
        return "";
    }

    public void init(String  getPeopleInfo){
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String result = (String) msg.obj;
//                try{
//                    JSONArray arr=new JSONArray(result);
//                }catch (){
//
//                }


                //ret = JSON.parse(result);
                Reader response = new StringReader(result);
//                System.out.println(result);
//                Toast.makeText(MsgListActivity.this,result, Toast.LENGTH_SHORT).show();
                lv_view=(ListView) findViewById(R.id.lv_view);
                //lv_view.setDividerHeight(20);
                myAdapter=new MyAdapter();
                myAdapter.setContext(MsgListActivity.this,codeId);

                try{
                    myAdapter.setmData(getList(response));
                    lv_view.setAdapter(myAdapter);
                }catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        };
        new Thread(new AccessNetwork("GET",getPeopleInfo,handler)).start();
    }


    //首次登入输入世界的名字
    public void NameFisrt(){
        LayoutInflater factory=LayoutInflater.from(this);
        View view=factory.inflate(R.layout.ordername, null);
        final EditText orderName=(EditText)findViewById(R.id.nameEdit);
        new AlertDialog.Builder(MsgListActivity.this)
                .setIcon(null)
                .setTitle("重新命名")
                .setView(view)
                .setNegativeButton(null, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which){

                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String Name = orderName.getText().toString();
            }
        }).show();
    }

}
