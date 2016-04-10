package com.example.stone.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


import com.example.stone.test.image.CircularImage;
import com.example.stone.test.model.User;
import com.example.stone.test.util.imgHelper;
import com.example.stone.test.util.userHelper;

import java.util.ArrayList;

/**
 * Created by stone on 4/9/16.
 */
public class avatorActivity extends Activity {
    private GridView gridView;
    private avatorAdapter adapter;
    private ArrayList<String> avators=new ArrayList<>();
    private ArrayList<Integer> imglist;
    private User user;
    private int avatorUsing;
    private ArrayList<Boolean> usingList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        setContentView(R.layout.activity_avator);
        gridView= (GridView) findViewById(R.id.avator_grid);

        Button btn= (Button) findViewById(R.id.button_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatorActivity.this.finish();
            }
        });


        gridView.setAdapter(new avatorAdapter(this,imglist, usingList));
    }

    private void init() {
        imglist=new ArrayList<>();
        usingList=new ArrayList<>();
        int[] imgs={R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5
                ,R.drawable.img6,R.drawable.img7,R.drawable.img8,R.drawable.img9};
        for (int i=0;i<9;i++){
            imglist.add(imgs[i]);
            usingList.add(Boolean.FALSE);
        }

        user=userHelper.getLocalUser(avatorActivity.this);
        String[] url=user.getAvator().split("");
        avatorUsing=Integer.valueOf(url[url.length-5])-1;
        usingList.set(avatorUsing,Boolean.TRUE);
    }

    class avatorAdapter extends BaseAdapter{
        private Context context;
        private ArrayList<Integer> avators;
        private ArrayList<Boolean> usingList;

        public avatorAdapter(Context context,ArrayList<Integer> avators,ArrayList<Boolean> using){
            super();
            this.avators=avators;
            this.context=context;
            this.usingList =using;
        }

        @Override
        public int getCount() {
            return avators.size();
        }

        @Override
        public Object getItem(int position) {
            return avators.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view=View.inflate(context,R.layout.avator_item,null);

            Integer img= (Integer) getItem(position);
            CircularImage avator = (CircularImage) view.findViewById(R.id.avator);
            avator.setImageResource(img);

            if (usingList.get(position) ==Boolean.TRUE) {
                CircularImage chosen = (CircularImage) view.findViewById(R.id.chosen);
                chosen.setImageResource(R.drawable.chosen);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Handler handler=new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            usingList.set(avatorUsing, Boolean.FALSE);
                            usingList.set(position, Boolean.TRUE);
                            avatorUsing=position;
                            notifyDataSetChanged();
                        }
                    };
                    imgHelper.changeAvatar(user, position + 1,handler);
                }
            });

            return view;
        }

        public void setChosen(int position){

        }
    }


}
