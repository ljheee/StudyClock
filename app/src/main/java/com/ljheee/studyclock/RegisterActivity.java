package com.ljheee.studyclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ljheee.studyclock.util.NetUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RegisterActivity extends AppCompatActivity {

    private EditText uid;
    private EditText password;

    private Button registerButton;
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uid = (EditText) findViewById(R.id.uid);
        password = (EditText) findViewById(R.id.password);

        registerButton = (Button) findViewById(R.id.registerButton);
        backButton = (Button) findViewById(R.id.backButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strUid = uid.getText().toString();
                String passwd = password.getText().toString();

                RequestParams param = new RequestParams();
                param.put("uid", strUid);
                param.put("password", passwd);
                NetUtil.register(param , new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        String s = new String(responseBody);
                        if("ok".equals(s)){
                            Log.e("register------" , "onSuccess");
                            LoginActivity.curUid = strUid;
                            startActivity(new Intent(RegisterActivity.this,PlanActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("register------" , "onFailure");
                    }
                });


            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
