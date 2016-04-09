package com.example.stone.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.SimpleAdapter;


import com.example.stone.test.image.CircularImage;
import com.example.stone.test.util.imgHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by stone on 4/9/16.
 */
public class avatorActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_avator);
        GridView gridView= (GridView) findViewById(R.id.avator_grid);

        ArrayList<String> avators= imgHelper.getAllAvator();

        gridView.setAdapter();
    }

    class avatorAdapter extends BaseAdapter{
        private Context context;
        private ArrayList<String> avators;

        public avatorAdapter(Context context,ArrayList<String> avators){
            super();
            this.avators=avators;
            this.context=context;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(context,R.layout.avator_item,null);

            String url= (String) getItem(position);

            ImageLoader imageLoader=ImageLoader.getInstance();

            CircularImage avator = (CircularImage) findViewById(R.id.avator);
            imageLoader.displayImage(url,avator);
        }
    }


}
