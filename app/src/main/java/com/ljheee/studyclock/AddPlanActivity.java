package com.ljheee.studyclock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ljheee.studyclock.bean.Plan;
import com.ljheee.studyclock.fragment.PlanFragment;

/**
 * 添加计划
 */
public class AddPlanActivity extends AppCompatActivity {

    private TextView planName;
    private Button okBtn;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        planName = (TextView) findViewById(R.id.textView1);
        okBtn = (Button) findViewById(R.id.okBtn);
        backBtn = (Button) findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPlanActivity.this.finish();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = planName.getText().toString();
                Plan plan = new Plan(name);
                PlanFragment.planList.add(plan);
//                getFragmentManager().findFragmentById(R.id.);
            }
        });



    }



}
