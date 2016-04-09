package com.example.stone.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.stone.test.image.CircularImage;
import com.example.stone.test.model.User;
import com.example.stone.test.util.userHelper;

public class MainActivity extends AppCompatActivity {

    private User user=null;
    private final static int SCANNIN_GREQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CircularImage cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);
        cover_user_photo.setImageResource(R.drawable.my_head);


        Button btn = (Button) findViewById(R.id.btn_goin);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user = userHelper.getNetUser();
                        Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
                    }
                }).start();
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, CaptureActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });

    }


    public void getuser(){
        user= userHelper.getLocalUser(MainActivity.this);

        if (user.getUserName()==null && user.getPasswordMD5()==null){

        }
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
}
