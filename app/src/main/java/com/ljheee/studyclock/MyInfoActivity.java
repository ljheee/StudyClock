package com.ljheee.studyclock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.ljheee.studyclock.adapter.PlanAdapter;
import com.ljheee.studyclock.bean.SinglePlan;

import java.util.ArrayList;

/**
 * 点击头像，查看个人信息
 */
public class MyInfoActivity extends AppCompatActivity {

    private TextView textViewUid;
    private String curUID;


    private ListView listView;
    ArrayList<SinglePlan> planList;
    PlanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);


        textViewUid = (TextView) findViewById(R.id.my_uid);
        Intent toMe = getIntent();
        if (toMe != null){//通过点击用户头像进入
            Bundle data = toMe.getExtras();
            if(data != null){
                curUID = data.getString("uid");
            }else{
                SharedPreferences sp = getSharedPreferences("ljheee", Context.MODE_PRIVATE);
                curUID = sp.getString("loginUID","");//如果取不到值就取后面的""
            }
        }else{//本机 主人
            SharedPreferences sp = getSharedPreferences("ljheee", Context.MODE_PRIVATE);
            curUID = sp.getString("loginUID","");//如果取不到值就取后面的""
        }


        textViewUid.setText("用户ID:"+curUID);


        listView = (ListView) findViewById(R.id.myInfo_ListView);
        initPlans();

    }

    private void initPlans() {

        planList = new ArrayList<>();
        adapter = new PlanAdapter(this , planList);
        listView.setAdapter(adapter);
        planList.add(new SinglePlan("ljh","背单词","2017-07-15 09:00:00","2017-07","com.ljheee.study"));
        planList.add(new SinglePlan("ljh","腾讯课堂学习视频","2017-07-15 10:00:00","2017-07","com.ljheee.study"));
        planList.add(new SinglePlan("ljh","知米听听力","2017-07-15 11:00:00","2017-07","com.ljheee.study"));
        planList.add(new SinglePlan("ljh","CSDN客户端问答","2017-07-15 12:30:00","2017-07","com.ljheee.study"));
        planList.add(new SinglePlan("ljh","背单词","2017-07-15 19:00:00","2017-07","com.ljheee.study"));
        adapter.notifyDataSetChanged();
    }












}
