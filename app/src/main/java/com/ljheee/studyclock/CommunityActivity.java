package com.ljheee.studyclock;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

public class CommunityActivity extends AppCompatActivity {

    public static final int ADD_QUESTION_CODE = 0;
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



    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutCommunity);
        linearLayout.setWeightSum(4.0f);


        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.onActionViewExpanded();
        searchView.setBackgroundColor(Color.parseColor("#F2F2F2"));
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
                Intent intent = new Intent(CommunityActivity.this ,  activityArray[index]);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ask_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.action_add_question://发布问题
                Intent intent = new Intent(this , AddQuestionActivity.class);
                startActivityForResult(intent,ADD_QUESTION_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case ADD_QUESTION_CODE:
                if(resultCode == Activity.RESULT_OK){
                    Bundle data = intent.getExtras();
//                    plan = (SinglePlan)data.getSerializable("plan");
//                    planList.add(plan);
//                    adapter.notifyDataSetChanged();

                }
                break;

            default:
                break;
        }
    }

}
