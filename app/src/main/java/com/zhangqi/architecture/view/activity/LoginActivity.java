package com.zhangqi.architecture.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.zhangqi.architecture.R;
import com.zhangqi.architecture.presenter.LoginPresenter;
import com.zhangqi.architecture.presenter.api.ILoginListener;
import com.zhangqi.architecture.util.Constant;

/**
 * Created by zhangqi on 16/11/13.
 */
public class LoginActivity extends Activity implements ILoginListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        LoginPresenter mPresenter = new LoginPresenter(this);
        EditText mLoginNameEt = (EditText) findViewById(R.id.et_name);
        EditText mLoginPasswordEt = (EditText) findViewById(R.id.et_password);
        String name = mLoginNameEt.getText().toString().trim();
        String password = mLoginPasswordEt.getText().toString().trim();
        mPresenter.doLogin(name, password);
    }

    @Override
    public void onLoginSuccess(String userInfo) {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra(Constant.LOGIN_RESPONSE,userInfo);
        startActivity(intent);
    }
}
