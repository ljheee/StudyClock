package com.ljheee.studyclock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * 点击头像，查看个人信息
 */
public class MyInfoActivity extends AppCompatActivity {

    private TextView textViewUid;
    private String curUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);


        textViewUid = (TextView) findViewById(R.id.textView_uid);
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


        textViewUid.setText("uid="+curUID);

    }
}
