package com.ljheee.studyclock.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ljheee.studyclock.AddPlanActivity;
import com.ljheee.studyclock.MyInfoActivity;
import com.ljheee.studyclock.R;
import com.ljheee.studyclock.adapter.PlanAdapter;
import com.ljheee.studyclock.bean.SinglePlan;

import java.util.ArrayList;

public class PlanFragment extends Fragment {


    TextView todayIntegral;
    ListView todayPlans;

    public static ArrayList<SinglePlan> planList;
    PlanAdapter adapter;

    public PlanFragment(){
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//指出fragment愿意添加item到选项菜单
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_plan, null);
        todayIntegral = (TextView) v.findViewById(R.id.today_integral);
        todayPlans = (ListView) v.findViewById(R.id.listview_plan);

        initPlans();
        adapter = new PlanAdapter(getContext(), planList);
        todayPlans.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }

    /**
     * 从服务器取数据
     */
    private void initPlans() {

        planList = new ArrayList<>();
        planList.add(new SinglePlan("ljh","ACM讨论","2017-06","2017-07","com.ljheee.study"));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        menu.add("添加").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        inflater.inflate(R.menu.main_tab_host, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getActivity(), item.getTitle(),Toast.LENGTH_SHORT).show();

        int id = item.getItemId();
        switch (id){
            case R.id.touxiang://点击头像，查看个人信息
                Intent intent2info = new Intent(getActivity() , MyInfoActivity.class);
                startActivity(intent2info);
                break;
            case R.id.action_add_plan://添加计划
                Intent intent2addplan = new Intent(getActivity() , AddPlanActivity.class);
                startActivity(intent2addplan);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
