package com.ljheee.studyclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RankActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    // 定义一个布局
    private LayoutInflater layoutInflater;


    private Class activityArray[] = { PlanActivity.class,
            CommunityActivity.class, RankActivity.class,
            PlayActivity.class };

    // 定义数组来存放按钮图片
    private int mImageViewArray[] = { R.drawable.news_main_btn,
            R.drawable.community_main_btn, R.drawable.goverment_main_btn,
            R.drawable.search_main_btn };

    // Tab选项卡的文字
    private String mTextviewArray[] = { "待办", "社区", "排行榜", "学习玩法" };


    private TextView allRank;
    private TextView askRank;
    private TextView todayRank;
    private ListView rankList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutRank);
        linearLayout.setWeightSum(4.0f);

        allRank = (TextView) findViewById(R.id.zongRank);
        askRank = (TextView) findViewById(R.id.askRank);
        todayRank = (TextView) findViewById(R.id.todayRank);
        rankList = (ListView) findViewById(R.id.rankList);

        allRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayRank.setVisibility(View.INVISIBLE);

            }
        });
        askRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayRank.setVisibility(View.INVISIBLE);

            }
        });


        initView();
    }


    private void initView() {
        // 实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        // 得到fragment的个数
        int count = mImageViewArray.length;
        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            View v =  getTabItemView(i);
            v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            linearLayout.addView(v);
        }



    }


    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(final int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankActivity.this ,  activityArray[index]);
                startActivity(intent);
            }
        });

        return view;
    }
}
