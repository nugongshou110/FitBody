package com.zhangqi.architecture.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zhangqi.architecture.R;
import com.zhangqi.architecture.presenter.AddPlanPresenter;
import com.zhangqi.architecture.presenter.api.IAddPlanListener;
import com.zhangqi.architecture.util.Constant;

/**
 * Created by zhangqi on 16/11/13.
 */
public class AddPlanActivity extends AppCompatActivity implements IAddPlanListener {
    private TextView mSelectStartDate;
    private TextView mSelectEndDate;
    private TextView mStartDate;
    private TextView mEndDate;
    private TextView mSelectMoney;
    private TextView mMoney;
    private EditText mPlanDetailEt;
    private AddPlanPresenter mPresenter;
    private TextView mClean;
    private TextView mConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plan);
        initView();
        initActivityBar();
        mPresenter = new AddPlanPresenter(this);
    }

    private void initActivityBar() {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("创建新的计划");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mSelectStartDate = (TextView) findViewById(R.id.start_date);
        mSelectEndDate = (TextView) findViewById(R.id.end_date);
        mSelectMoney = (TextView) findViewById(R.id.money);
        mStartDate = (TextView) findViewById(R.id.tv_start_date);
        mEndDate = (TextView) findViewById(R.id.tv_end_date);
        mMoney = (TextView) findViewById(R.id.tv_money);
        mPlanDetailEt = (EditText) findViewById(R.id.et_detail);
        mClean = (TextView) findViewById(R.id.clean);
        mConfirm = (TextView) findViewById(R.id.confirm);
        mSelectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog(true);
            }
        });
        mSelectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog(false);
            }
        });
        mSelectMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBalanceDialog();
            }
        });
        mClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartDate.setText("");
                mEndDate.setText("");
                mMoney.setText("");
                mPlanDetailEt.setText("");
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = getIntent().getIntExtra(Constant.USER_ID, -1);
                mPresenter.doAddPlan(userId, mStartDate.getText().toString(),
                        mEndDate.getText().toString(), mMoney.getText().toString(),
                        mPlanDetailEt.getText().toString());
            }
        });
    }

    private void showDatePickDialog(final boolean start) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_date, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.start_date_picker);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                String finalDay;
                if (day < 10) {
                    finalDay = "0" + day;
                } else {
                    finalDay = "" + day;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(year).append("-").append(month + 1).append("-").append(finalDay);
                if (start) {
                    mStartDate.setText(stringBuilder.toString());
                } else {
                    mEndDate.setText(stringBuilder.toString());
                }
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

    private void showBalanceDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_check_balance, null);
        builder.setView(view);
        RadioButton rb1 = (RadioButton) view.findViewById(R.id.rb1);
        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMoney.setText(buttonView.getText());
                }
            }
        });
        RadioButton rb2 = (RadioButton) view.findViewById(R.id.rb2);
        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMoney.setText(buttonView.getText());
                }
            }
        });
        RadioButton rb3 = (RadioButton) view.findViewById(R.id.rb3);
        rb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMoney.setText(buttonView.getText());
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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

    @Override
    public void onAddPlanSuccess() {
        Intent intent = new Intent(AddPlanActivity.this, MainActivity.class);
        intent.putExtra(Constant.ADD_PLAN, Constant.ADD_PLAN_SUCCESS);
        startActivity(intent);
        finish();
    }
}
