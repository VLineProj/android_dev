package com.example.stone.test.MessageList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stone.test.R;
import com.example.stone.test.entities.People_information;
import com.example.stone.test.image.CircularImage;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 润 on 2016/4/9.
 */
public class MyAdapter extends BaseAdapter{
    private List<People_information> mData;
    private Context context;
    private String codeId;
    public void setmData(List mData) {
        this.mData = mData;
    }
    public void setContext(Context context,String codeId) {
        this.context = context;
        this.codeId=codeId;
    }
    //决定了列表item显示的个数
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }
    //根据position获取对应item的内容
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }
    //获取对应position的item的ID
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    //创建列表item视图
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        View view= View.inflate(context, R.layout.prview, null);
        //获取item对应的数据对象
        final People_information people=mData.get(position);
        //初始化view
        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        CircularImage iv_picture= (CircularImage) view.findViewById(R.id.iv_picture);
        iv_picture.setImageResource(R.drawable.img1);
        TextView tv_nickname=(TextView) view.findViewById(R.id.tv_nickname);
        TextView tv_time=(TextView)view.findViewById(R.id.tv_time);
        TextView tv_description=(TextView) view.findViewById(R.id.tv_description);
        //绑定数据到view
        //iv_picture.setImageResource(people.getDraw_Id());
        imageLoader.displayImage(people.getDrawUrl(), iv_picture);//loader.displayImage有很多参数不同的方法，方便我们针对 不同情况使用
        tv_nickname.setText(people.getNickname());
        tv_time.setText(people.getTime());
        tv_description.setText(people.getDescription());

        CardView cardView= (CardView) view.findViewById(R.id.cardlist);
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intentAdd=new Intent();
                intentAdd.setClass(context, AddContent.class);
                intentAdd.putExtra("title", "@" + people.getNickname());
                intentAdd.putExtra("msgid",people.getMsgId());
                intentAdd.putExtra("codeid",codeId);
                context.startActivity(intentAdd);
                return false;
            }
        });
        return view;
    }

}
