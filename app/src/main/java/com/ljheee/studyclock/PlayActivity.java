package com.ljheee.studyclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ljheee.studyclock.bean.App;
import com.ljheee.studyclock.util.GlideImageLoader;
import com.ljheee.studyclock.util.NetUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thoughtworks.xstream.XStream;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

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



    private TextView textView;
    private ListView listViewApp;
    List<String> dataList;
    ArrayAdapter adapter ;


    private String[] images = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutPlay);
        linearLayout.setWeightSum(4.0f);



        textView = (TextView) findViewById(R.id.study_play);
        initView();
        Banner banner = (Banner) findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(Arrays.asList(images));
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        listViewApp = (ListView) findViewById(R.id.listView_applist);
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this ,android.R.layout.simple_expandable_list_item_1,dataList);
        listViewApp.setAdapter(adapter);

        RequestParams param = new RequestParams();
        param.put("defaultAppRankList", "default");
        NetUtil.getAppList(param , new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);

                XStream xStream = new XStream();
                List<App> listt = (List<App>) xStream.fromXML(s);
                for (int i=0;i<listt.size();i++) {
                    dataList.add(listt.get(i).getAppPkg()+"---排名:"+(i+1));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("getAppList------" , "onFailure");
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
                Intent intent = new Intent(PlayActivity.this ,  activityArray[index]);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.action_show_type://点击查看---学习玩法分类
                PopupMenu menu = new PopupMenu(this, textView);
                // 加载菜单文件
                menu.inflate(R.menu.menu_play_type);

                // 添加菜单项点击监听器
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id= item.getItemId();
                        switch (id){
                            case R.id.action_english:
                                Toast.makeText(PlayActivity.this ,"english" ,Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_fanyi:
                                Toast.makeText(PlayActivity.this ,"action_fanyi" ,Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_online_study:
                                Toast.makeText(PlayActivity.this ,"action_online_study" ,Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_it:
                                Toast.makeText(PlayActivity.this ,"action_it" ,Toast.LENGTH_SHORT).show();
                                break;
                        }

                        return true;
                    }
                });
                menu.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
