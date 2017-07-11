package com.ljheee.studyclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ljheee.studyclock.bean.SinglePlan;
import com.ljheee.studyclock.util.NetUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thoughtworks.xstream.XStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppService extends Service {

    List<SinglePlan> planList;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    //1.创建通知管理器
    private NotificationManager manager;
    NotificationCompat.Builder builder;

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
        builder2.setContentTitle("Title"+System.currentTimeMillis());
        builder2.setContentText("Welcome to 汇学App");
        Notification notification = builder2.build();

        startForeground(1, notification);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        new NetThread().start();
        new NotifyThread().start();

        return START_STICKY;
    }

    /**
     * 获取数据库，uid 用户的今日计划
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


}
