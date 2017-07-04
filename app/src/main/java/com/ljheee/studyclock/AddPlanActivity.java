package com.ljheee.studyclock;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ljheee.studyclock.bean.MyAppInfo;
import com.ljheee.studyclock.bean.SinglePlan;
import com.ljheee.studyclock.util.ApkTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加计划
 */
public class AddPlanActivity extends AppCompatActivity {


    private EditText planName;
    private EditText startTime;
    private EditText endTime;
    private Spinner spinner;
    private Button okBtn;
    private Button backBtn;

    private String uid;
    private String name;
    private String start;
    private String end;
    private String appPkg;

    List<MyAppInfo> dataList;
    private AsyTask asyTask;//异步任务
    private AppAdapter mAppAdapter;

    public Handler mHandler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        planName = (EditText) findViewById(R.id.editText);
        startTime = (EditText) findViewById(R.id.editText2);
        endTime = (EditText) findViewById(R.id.editText3);
        spinner = (Spinner) findViewById(R.id.spinner);
        okBtn = (Button) findViewById(R.id.okBtn);
        backBtn = (Button) findViewById(R.id.backBtn);

        mAppAdapter = new AppAdapter();
        spinner.setAdapter(mAppAdapter);

        asyTask = new AsyTask();
        asyTask.execute();
        Log.e("AddPlan=onCreate","asyTask.execute");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MyAppInfo app = (MyAppInfo) mAppAdapter.getItem(position);
                appPkg = app.getAppName();
                Toast.makeText(AddPlanActivity.this, "已选择："+appPkg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddPlanActivity.this, "请选择", Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPlanActivity.this.finish();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = planName.getText().toString();
                start = startTime.getText().toString();
                end = endTime.getText().toString();

                SinglePlan plan = new SinglePlan(uid,name,start,end,appPkg);
//                getFragmentManager().findFragmentById(R.id.);

                Intent intent = getIntent();
                Bundle data = new Bundle();
                data.putSerializable("plan",  plan);
                intent.putExtras(data);
                AddPlanActivity.this.setResult(Activity.RESULT_OK,intent);//设置返回码为Activity.RESULT_OK，返回intent

                //本Activity结束，退出该Activity
                AddPlanActivity.this.finish();
            }
        });
    }



    /**
     * 异步任务--避免：Service开启新的线程，并利用Handler机制，让Service中新开启的线程和主线程进行了通信，从而利用主线程改变了UI。
     * 没有优先级，只是在前台进程
     * @author ljheee
     *
     */
    private class AsyTask extends AsyncTask<Void, Void, Void> {


        /**
         * 预处理
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 其他线程执行，不能做UI改变
         */
        @Override
        protected Void doInBackground(Void... value) {
            dataList = ApkTool.scanLocalInstallAppList(AddPlanActivity.this.getPackageManager());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mAppAdapter.setData(dataList);
                }
            });
            Log.e("doInBackground******",""+dataList.size());
            return null;
        }
    }

    /**
     * 内部类
     */
    class AppAdapter extends BaseAdapter {

        List<MyAppInfo> myAppInfos = new ArrayList<MyAppInfo>();

        public List<MyAppInfo> getData() {
            return myAppInfos;
        }

        public void setData(List<MyAppInfo> myAppInfos) {
            this.myAppInfos = myAppInfos;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (myAppInfos != null && myAppInfos.size() > 0) {
                return myAppInfos.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (myAppInfos != null && myAppInfos.size() > 0) {
                return myAppInfos.get(position);
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
            MyAppInfo myAppInfo = myAppInfos.get(position);
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.install_app_item, null);
                mViewHolder.iv_app_icon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
                mViewHolder.tx_app_name = (TextView) convertView.findViewById(R.id.tv_app_name);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.iv_app_icon.setImageDrawable(myAppInfo.getImage());
            mViewHolder.tx_app_name.setText(myAppInfo.getAppName());
            return convertView;
        }

        class ViewHolder {

            ImageView iv_app_icon;
            TextView tx_app_name;
        }
    }

}
