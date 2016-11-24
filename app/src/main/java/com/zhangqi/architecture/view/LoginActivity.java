package com.zhangqi.architecture.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.zhangqi.architecture.R;

/**
 * Created by zhangqi on 16/11/13.
 */
public class LoginActivity extends Activity {
    private EditText mLoginNameEt;
    private EditText mLoginPasswordEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mLoginNameEt = (EditText) findViewById(R.id.et_name);
        mLoginPasswordEt = (EditText) findViewById(R.id.et_password);
    }
}
