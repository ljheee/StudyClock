package com.ljheee.studyclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ljheee.studyclock.bean.User;
import com.ljheee.studyclock.util.NetUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

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

    private List<User> userList;
    private RankAdapter mAdapter;



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

                RequestParams param = new RequestParams();
                param.put("rankType", "b");
                NetUtil.getRankList(param , new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        String s = new String(responseBody);

                        XStream xStream = new XStream();
                        userList = (List<User>) xStream.fromXML(s);
                        mAdapter.setData(userList);
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("login------" , "onFailure");
                    }
                });
            }
        });
        askRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams param = new RequestParams();
                param.put("rankType", "c");
                NetUtil.getRankList(param , new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        String s = new String(responseBody);

                        XStream xStream = new XStream();
                        userList = (List<User>) xStream.fromXML(s);
                        mAdapter.setData(userList);
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("login------" , "onFailure");
                    }
                });
            }
        });



        initView();
        mAdapter = new RankAdapter();
        rankList.setAdapter(mAdapter);

        RequestParams param = new RequestParams();
        param.put("rankType", "a");
        NetUtil.getRankList(param , new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);

                XStream xStream = new XStream();
                userList = (List<User>) xStream.fromXML(s);
                mAdapter.setData(userList);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("getRankList------" , "onFailure");
                userList = new ArrayList<User>();
                userList.add(new User("ada","ada"));
                mAdapter.setData(userList);
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
                Intent intent = new Intent(RankActivity.this ,  activityArray[index]);
                startActivity(intent);
            }
        });

        return view;
    }



    /**
     * 内部类
     */
    class RankAdapter extends BaseAdapter {

        List<User> ranks = new ArrayList<User>();

        public List<User> getData() {
            return ranks;
        }

        public void setData(List<User> ranks) {
            this.ranks = ranks;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (ranks != null && ranks.size() > 0) {
                return ranks.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (ranks != null && ranks.size() > 0) {
                return ranks.get(position);
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
            final User user = ranks.get(position);
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.user_rank_item, null);
                mViewHolder.iv_user_icon = (ImageView) convertView.findViewById(R.id.iv_user_icon);
                mViewHolder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
                mViewHolder.iv_user_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击用户头像，进入“个人中心”
                        Intent intent = new Intent(RankActivity.this , MyInfoActivity.class);
                        Bundle data = new Bundle();
                        data.putString("uid",  user.getUid());
                        intent.putExtras(data);
                        startActivity(intent);
                    }
                });

                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.iv_user_icon.setImageResource(R.mipmap.ic_plan);
            mViewHolder.tv_user_name.setText(user.getUid()+"---排名:"+(position+1));
            return convertView;
        }

        class ViewHolder {
            ImageView iv_user_icon;
            TextView tv_user_name;
        }
    }


}
