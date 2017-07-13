package com.ljheee.studyclock;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.ljheee.studyclock.bean.QuestionRecord;
import com.ljheee.studyclock.util.NetUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

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

    private ListView quesListView;
    private QuestionAdapter adapter;
    private List<QuestionRecord> dataList;


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
        searchView.setFocusable(false);
        searchView.setBackgroundColor(Color.parseColor("#F2F2F2"));
        initView();



        quesListView = (ListView) findViewById(R.id.listView_questiom);
        adapter = new QuestionAdapter();
        quesListView.setAdapter(adapter);

        RequestParams param = new RequestParams();
        param.put("defaultQuestionList", "default");
        NetUtil.getQuesList(param , new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                XStream xStream = new XStream();
                dataList = (List<QuestionRecord>) xStream.fromXML(s);
                adapter.setData(dataList);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("getQuesList------" , "onFailure");
                dataList = new ArrayList<QuestionRecord>();
                dataList.add(new QuestionRecord("testUID",0,"Java SE问题？","Java中，构造方法是否能继承？"));
                adapter.setData(dataList);
            }
        });

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



    class QuestionAdapter extends BaseAdapter {

        List<QuestionRecord> ques = new ArrayList<QuestionRecord>();

        public List<QuestionRecord> getData() {
            return ques;
        }

        public void setData(List<QuestionRecord> ques) {
            this.ques = ques;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (ques != null && ques.size() > 0) {
                return ques.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (ques != null && ques.size() > 0) {
                return ques.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder;
            final QuestionRecord queRecord = ques.get(position);
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.question_item, null);
                mViewHolder.iv_ques_icon = (ImageView) convertView.findViewById(R.id.iv_ques_icon);
                mViewHolder.tv_ques_title = (TextView) convertView.findViewById(R.id.tv_ques_title);
                mViewHolder.iv_ques_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击头像，进入“问题页”
                        Intent intent = new Intent(CommunityActivity.this , ShowQuestionActivity.class);
                        Bundle data = new Bundle();
                        data.putInt("qid",  queRecord.getQid());
                        intent.putExtras(data);
                        startActivity(intent);
                    }
                });

                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.iv_ques_icon.setImageResource(R.mipmap.ic_plan);
            mViewHolder.tv_ques_title.setText("问题摘要："+queRecord.getTitle());
            return convertView;
        }

        class ViewHolder {
            ImageView iv_ques_icon;
            TextView tv_ques_title;
        }
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

}
