package com.ljheee.studyclock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ljheee.studyclock.bean.Comment;
import com.ljheee.studyclock.bean.QuestionDetail;
import com.ljheee.studyclock.util.NetUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

public class ShowQuestionActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewDetail;


    private EditText editText;

    private Button ireply;
    private Button backButton;

    private ListView replyListView;
    CommentAdapter adapter;
    List<Comment> comms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);

        Intent toMe = getIntent();
        final int qid = toMe.getExtras().getInt("qid");

        textViewTitle = (TextView) findViewById(R.id.show_ques_title);
        textViewDetail = (TextView) findViewById(R.id.show_ques_detail);
        editText = (EditText) findViewById(R.id.intput_reply);

        ireply = (Button) findViewById(R.id.button_i_reply);
        backButton = (Button) findViewById(R.id.button_i_back);

        replyListView = (ListView) findViewById(R.id.listView_reply);
        adapter = new CommentAdapter();
        replyListView.setAdapter(adapter);


        RequestParams param = new RequestParams();
        param.put("qid", qid);
        NetUtil.getQuesDetail(param , new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);

                XStream xStream = new XStream();
                QuestionDetail qd = (QuestionDetail) xStream.fromXML(s);
                textViewTitle.setText("问题摘要："+qd.getTitle());
                textViewDetail.setText("问题详情："+qd.getDetail());
                comms = qd.getComments();
                adapter.setData(comms);


            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("getAppList------" , "onFailure");
            }
        });



        ireply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences("ljheee", Context.MODE_PRIVATE);
                String uid = sp.getString("loginUID","");
                //qid
                String content = editText.getText().toString();
                RequestParams param = new RequestParams();
                param.put("reply_uid", uid);
                param.put("qid", qid);
                param.put("reply_content", content);
                NetUtil.reply(param , new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        String s = new String(responseBody);
                        if("ok".equals(s)){
                            Toast.makeText(ShowQuestionActivity.this ,"已回复" ,Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e("getAppList------" , "onFailure");
                    }
                });

                Toast.makeText(ShowQuestionActivity.this ,"回复成功" ,Toast.LENGTH_SHORT).show();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }




    class CommentAdapter extends BaseAdapter {

        List<Comment> comms = new ArrayList<Comment>();

        public List<Comment> getData() {
            return comms;
        }

        public void setData(List<Comment> comms) {
            this.comms = comms;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (comms != null && comms.size() > 0) {
                return comms.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (comms != null && comms.size() > 0) {
                return comms.get(position);
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
            final Comment comm = comms.get(position);
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.comment_item, null);
                mViewHolder.replyUid = (TextView) convertView.findViewById(R.id.reply_uid);
                mViewHolder.replyTime = (TextView) convertView.findViewById(R.id.reply_time);
                mViewHolder.replyContent = (TextView) convertView.findViewById(R.id.reply_content);
                mViewHolder.zanButton = (Button)findViewById(R.id.button_zan);

                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            mViewHolder.replyUid.setText(comm.getUid());
            mViewHolder.replyTime.setText(comm.getAddTime());
            mViewHolder.replyContent.setText(comm.getContent());
            mViewHolder.zanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams param = new RequestParams();
                    param.put("cid", comm.getCid());
                    NetUtil.doDianZan(param , new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                            String s = new String(responseBody);
                            if("ok".equals(s)){
                                Toast.makeText(ShowQuestionActivity.this ,"已点赞" ,Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                            Log.e("getAppList------" , "onFailure");
                        }
                    });
                }
            });

            return convertView;
        }

        class ViewHolder {

            TextView replyUid;
            TextView replyTime;
            TextView replyContent;
            Button zanButton;
        }
    }
}
