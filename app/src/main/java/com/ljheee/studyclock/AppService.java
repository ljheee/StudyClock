package com.ljheee.studyclock;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ljheee.studyclock.bean.SinglePlan;
import com.ljheee.studyclock.util.NetUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thoughtworks.xstream.XStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class AppService extends Service {

    List<SinglePlan> planList;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    boolean isLock = false;

    //第一次打开“有权查看使用情况的应用”
    private boolean isFirst = true;
    ArrayList<String> forbidApps = new ArrayList<>();

    //1.创建通知管理器
    private NotificationManager manager;
    NotificationCompat.Builder builder;


    Handler  handler = new Handler(){

        public void handleMessage(android.os.Message msg){

            //getTaskPackname();
//            if("com.ljheee.jokes".equals(getTaskPackname())){
            if(forbidApps.contains(getTaskPackname())){
                Intent intent = new Intent(getBaseContext(),PlanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intent);
            }

            handler.sendEmptyMessageDelayed(1, 500);//500毫秒“轮询”一次，查看栈顶app
        };
    };

    public AppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);

        Notification.Builder builder2 = new Notification.Builder(this);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, PlanActivity.class), 0);
        builder2.setContentIntent(contentIntent);
        builder2.setSmallIcon(R.mipmap.ic_launcher);
        builder2.setTicker("Foreground Service Start");
        builder2.setContentTitle(sdf.format(new Date()));
        builder2.setContentText("Welcome to 汇学App");
        Notification notification = builder2.build();

        startForeground(1, notification);

        //..................
        forbidApps.add("com.tencent.mm");
        forbidApps.add("com.tencent.mobileqq");

        goToAndroidSettings();

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

//        new NetThread().start();
        getTodayPlans();
        new NotifyThread().start();

        return START_STICKY;
    }

    /**
     * 获取数据库，uid 用户的今日计划
     * 不能在 子线程中执行
     */
    private void getTodayPlans(){
        SharedPreferences sp = getSharedPreferences("ljheee", Context.MODE_PRIVATE);
        String uid = sp.getString("loginUID","");
        RequestParams param = new RequestParams();
        param.put("uid", uid);
        NetUtil.getTodayPlans(param , new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                XStream xStream = new XStream();
                planList = (List<SinglePlan>) xStream.fromXML(s);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("getTodayPlans------" , "onFailure");
            }
        });
    }




    private void compareTime(List<SinglePlan> planList){
        if(planList == null||planList.size()==0){
            return;
        }

        for (int i = 0; i < planList.size(); i++){
            SinglePlan plan = planList.get(i);
            try {
                Date startDate = sdf.parse(plan.getStartTime());
                Date nowDate = new Date();
                if(startDate.equals(nowDate)||startDate.after(nowDate)){
                    //Notification
                    sendNotification(plan);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }


    class NetThread extends Thread{
        @Override
        public void run() {
            while (true) {
                try {
                    getTodayPlans();
                    Thread.sleep(60*60*1000);// 1 hour
                } catch (InterruptedException e) {
                    // 将 InterruptedException 定义在while循环之外，意味着抛出 InterruptedException 异常时，终止线程。
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 对比uid用户，今日计划开始时间，在开始时提示
     */
    class NotifyThread extends Thread{
        @Override
        public void run() {
            while (true) {
                try {
                    compareTime(planList);
                    Thread.sleep(1000);// 1s
                } catch (InterruptedException e) {
                    // 将 InterruptedException 定义在while循环之外，意味着抛出 InterruptedException 异常时，终止线程。
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 通知Notification，开始计划
     * @param plan
     */
    public  void sendNotification(SinglePlan plan){
        builder.setContentTitle("计划开始通知")
                .setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText("计划名:"+plan.getPlanName()+"\n开始时间:"+plan.getStartTime());
        manager.notify(100, builder.build());
    }



    /**
     * 打开“有权查看使用情况的应用”
     * Android 5.0以后版本
     */
    public void goToAndroidSettings(){
        //打开--com.android.settings
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }

    /**
     * 获取栈顶应用程序--包名
     * @return
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private String getTaskPackname() {
        boolean isNoSwitch = isNoSwitch();
        Log.e("LJHEEE", "isNoSwitch=: " + isNoSwitch);

        String currentApp = "CurrentNULL";
        //LOLLIPOP = API 21,Android 5.0
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);


            //5.1以上，如果不打开此设置，queryUsageStats获取到的是size为0的list
//            if(isFirst||(!isNoSwitch())||appList.size() == 0 || appList == null){
            if((!isNoSwitch())&&isFirst){
                isFirst = false;
                goToAndroidSettings();
            }else{
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }
        Log.e("TAG", "Current App in foreground is: " + currentApp);
        return currentApp;
    }

    /**
     * 判断当前设备中有没有 “有权查看使用权限的应用” 这个选项
     * @return
     */
    private boolean isNoOption() {
        PackageManager packageManager = getApplicationContext().getPackageManager();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * 判断调用该设备中“有权查看使用权限的应用”这个选项的APP有没有打开
     * @return  此APP打开这个选项，就返回true
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean isNoSwitch() {
        long ts = System.currentTimeMillis();
        UsageStatsManager usageStatsManager = (UsageStatsManager) getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST, 0, ts);
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return false;
        }
        return true;
    }

}
