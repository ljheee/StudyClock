package com.ljheee.studyclock.util;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NetUtil {


    private static final String URL = "http://172.23.80.81:8080/UploadDemo";
//    private static HttpClient httpClient = new DefaultHttpClient();

    private static AsyncHttpClient mClient = new AsyncHttpClient();
    static {
        mClient.setTimeout(10000);
    }



    /**
     * 用户登录
     * @param params
     * @param handler
     */
    public static void login(RequestParams params,
                             AsyncHttpResponseHandler handler){
        String url = "http://172.22.88.160:8080/OurStudy/loginServlet";
        mClient.post(url , params , handler);
    }


    public static void register(RequestParams params,
                             AsyncHttpResponseHandler handler){
        String url = "http://172.22.88.160:8080/OurStudy/registerServlet";
        mClient.post(url , params , handler);
    }

    public static void addPlan(RequestParams params,
                             AsyncHttpResponseHandler handler){
        String url = "http://172.22.88.160:8080/OurStudy/addPlanServlet";
        mClient.post(url , params , handler);
    }

    public static void getRankList(RequestParams params,
                             AsyncHttpResponseHandler handler){
        String url = "http://172.22.88.160:8080/OurStudy/rankServlet";
        mClient.post(url , params , handler);
    }

    public static void pubQuestion(RequestParams params,
                             AsyncHttpResponseHandler handler){
        String url = "http://172.22.88.160:8080/OurStudy/pubQueServlet";
        mClient.post(url , params , handler);
    }

    public static void getQuesList(RequestParams params,
                             AsyncHttpResponseHandler handler){
        String url = "http://172.22.88.160:8080/OurStudy/getQuesListServlet";
        mClient.post(url , params , handler);
    }

    public static void getAppList(RequestParams params,
                             AsyncHttpResponseHandler handler){
        String url = "http://172.22.88.160:8080/OurStudy/getAppRankServlet";
        mClient.post(url , params , handler);
    }

    public static void getQuesDetail(RequestParams params,
                             AsyncHttpResponseHandler handler){
        String url = "http://172.22.88.160:8080/OurStudy/getQuesDetailServlet";
        mClient.post(url , params , handler);
    }

    public static void doDianZan(RequestParams params,
                             AsyncHttpResponseHandler handler){
        String url = "http://172.22.88.160:8080/OurStudy/dianZanServlet";
        mClient.post(url , params , handler);
    }
    public static void reply(RequestParams params,
                             AsyncHttpResponseHandler handler){
        String url = "http://172.22.88.160:8080/OurStudy/replyQuesServlet";
        mClient.post(url , params , handler);
    }






}
