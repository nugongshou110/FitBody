package com.zhangqi.architecture.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangqi.architecture.R;

/**
 * Created by zhangqi on 16/11/13.
 */
public class AddPlanActivity extends Activity {
    private TextView mSelectStartDate;
    private TextView mSelectEndDate;
    private TextView mStartDate;
    private TextView mEndDate;
    private TextView mSelectMoney;
    private TextView mMoney;
    private EditText mPlanDetailEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plan);
        initView();
    }

    private void initView() {
        mSelectStartDate = (TextView) findViewById(R.id.start_date);
        mSelectEndDate = (TextView) findViewById(R.id.end_date);
        mSelectMoney = (TextView) findViewById(R.id.money);
        mStartDate = (TextView) findViewById(R.id.tv_start_date);
        mEndDate = (TextView) findViewById(R.id.tv_end_date);
        mMoney = (TextView) findViewById(R.id.tv_money);
        mPlanDetailEt = (EditText) findViewById(R.id.et_detail);
        mSelectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDateDialog();
            }
        });
    }

    private void showStartDateDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_start_date, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.start_date_picker);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(year).append("/").append(month+1).append("/").append(day);
                mStartDate.setText(stringBuilder.toString());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
