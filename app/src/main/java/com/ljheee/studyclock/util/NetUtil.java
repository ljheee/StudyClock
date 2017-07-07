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




}
