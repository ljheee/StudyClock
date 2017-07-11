package com.ljheee.studyclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.ljheee.studyclock.adapter.PlanAdapter;
import com.ljheee.studyclock.bean.SinglePlan;

import java.util.ArrayList;

public class PlanActivity extends AppCompatActivity {

    TextView todayIntegral;
    ListView todayPlans;
    ListView leftListView;
    LinearLayout linearLayout;
    private DrawerLayout drawerLayout;

    ArrayList<SinglePlan> planList;
    PlanAdapter adapter;

    public static final int ADD_PLAN_CODE = 0;
    public static final int LOGIN_CODE = 1;
    SinglePlan plan;

    // 定义一个布局
    private LayoutInflater layoutInflater;
    private ActionBarDrawerToggle toggle;
    private volatile String userName = "  登录";
    private String userInfo = "  个人中心";
    private String userConf = "  设置";
    private String userExit = "  退出";
    private String[] leftData = {userName, userInfo, userConf, userExit};

    private Class activityArray[] = { PlanActivity.class,
            CommunityActivity.class, RankActivity.class,
            PlayActivity.class };

    // 定义数组来存放按钮图片
    private int mImageViewArray[] = { R.drawable.news_main_btn,
            R.drawable.community_main_btn, R.drawable.goverment_main_btn,
            R.drawable.search_main_btn };

    // Tab选项卡的文字
    private String mTextviewArray[] = { "待办", "社区", "排行榜", "学习玩法" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        todayIntegral = (TextView) findViewById(R.id.today_integral);
        todayPlans = (ListView) findViewById(R.id.listview_plan);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.setWeightSum(4.0f);


        initView();

        initPlans();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // 设置抽屉的阴影
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.END);

        // 创建抽屉把手
        toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.drawable.ic_menu_white_24dp,
                R.string.drawer_open,
                R.string.drawer_close) {

            // 抽屉打开
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // 重建选项菜单
                invalidateOptionsMenu();
            }

            // 抽屉关闭
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // 重建选项菜单
                invalidateOptionsMenu();
            }
        };

        // 同步状态（更新 Home 位置显示的图标）
        toggle.syncState();

        // 设置监听器
        drawerLayout.setDrawerListener(toggle);
        initLeftDrawer();

        //开启服务
        Intent startService = new Intent(this, AppService.class);
        startService(startService);
    }
    /**
     * 构造：左侧抽屉
     */
    private void initLeftDrawer() {
        leftListView = (ListView) findViewById(R.id.leftListView);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, leftData);

        leftListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        leftListView.setAdapter(adapter);
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 关闭抽屉
                drawerLayout.closeDrawer(GravityCompat.START);

                switch (position) {
                    case 0://用户登录
                        Intent loginloginIntent = new Intent(PlanActivity.this, LoginActivity.class);
                        startActivityForResult(loginloginIntent, LOGIN_CODE);
                        break;
                    case 1://用户信息
                        Intent userIntent = new Intent(PlanActivity.this, MyInfoActivity.class);
                        startActivity(userIntent);
                        break;
                    case 2://设置

                        break;
                    case 3:
                        finish();//退出应用程序
                        System.exit(0);
                        break;
                }
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
            v.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
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
                Intent intent = new Intent(PlanActivity.this , activityArray[index]);
                startActivity(intent);
            }
        });
        return view;
    }


    /**
     * 从服务器取数据
     */
    private void initPlans() {

        planList = new ArrayList<>();
        adapter = new PlanAdapter(this , planList);
        todayPlans.setAdapter(adapter);
        planList.add(new SinglePlan("ljh","ACM讨论","2017-06","2017-07","com.ljheee.study"));
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_tab_host, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this , item.getTitle(),Toast.LENGTH_SHORT).show();

        int id = item.getItemId();
        switch (id){
            case R.id.touxiang://点击头像，查看个人信息
                Intent intent2info = new Intent(this , MyInfoActivity.class);
                startActivity(intent2info);
                break;
            case R.id.action_add_plan://添加计划
                Intent intent2addplan = new Intent(this , AddPlanActivity.class);
                startActivityForResult(intent2addplan , ADD_PLAN_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case ADD_PLAN_CODE:
                if(resultCode == Activity.RESULT_OK){
                    Bundle data = intent.getExtras();
                    plan = (SinglePlan)data.getSerializable("plan");
                    planList.add(plan);
                    adapter.notifyDataSetChanged();
                }
                break;

            default:
                break;
        }
    }
}
