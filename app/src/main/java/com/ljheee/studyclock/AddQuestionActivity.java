package com.ljheee.studyclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ljheee.studyclock.util.NetUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText quesTitle;
    private EditText quesDetail;
    private Spinner quesTypeSpinner;
    private Button addQues;
    private Button cancleQues;
    ArrayAdapter adapter;
    List<String> quesTypeList = new ArrayList<>();
    String questionType;

    public Handler mHandler = new Handler();
    AsyTaskQuestionType asyTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        quesTitle = (EditText) findViewById(R.id.ques_title);
        quesDetail = (EditText) findViewById(R.id.ques_detail);
        addQues = (Button) findViewById(R.id.addQues_button);
        cancleQues = (Button) findViewById(R.id.back_2_button);

        quesTypeSpinner = (Spinner) findViewById(R.id.ques_type_spinner);
        adapter = new ArrayAdapter<String>(this ,android.R.layout.simple_expandable_list_item_1, quesTypeList);
        quesTypeSpinner.setAdapter(adapter);
        quesTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionType = (String) adapter.getItem(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        asyTask = new AsyTaskQuestionType();
        asyTask.execute();


        cancleQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("ljheee", Context.MODE_PRIVATE);
                String uid = sp.getString("loginUID","");
                String title = quesTitle.getText().toString();
                String detail = quesDetail.getText().toString();

                RequestParams param = new RequestParams();
                param.put("uid", uid);
                param.put("title", title);
                param.put("detail", detail);
                param.put("questionType", questionType);
                NetUtil.pubQuestion(param , new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        String s = new String(responseBody);
                        if("ok".equals(s)){
                            Log.e("pubQuestion------" , "onSuccess");
                            Toast.makeText(AddQuestionActivity.this, "提问成功" ,Toast.LENGTH_SHORT).show();
                            AddQuestionActivity.this.finish();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("pubQuestion------" , "onFailure");
                    }
                });
                Toast.makeText(AddQuestionActivity.this,"pubQuestion",Toast.LENGTH_SHORT).show();
            }
        });


    }


    /**
     * 异步任务
     * 获取--Question 类型列表
     */
    private class AsyTaskQuestionType extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... value) {

//            quesTypeList = NetUtil.getTypeList();
            quesTypeList.add("英语");
            quesTypeList.add("翻译");
            quesTypeList.add("IT技术");
            quesTypeList.add("文学");
            quesTypeList.add("医药医学");
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
            Log.e("AsyTaskQuestionType****",""+quesTypeList.size());
            return null;
        }
    }
}
