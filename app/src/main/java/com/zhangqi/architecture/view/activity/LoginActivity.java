package com.zhangqi.architecture.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangqi.architecture.R;
import com.zhangqi.architecture.model.bean.UserInfo;
import com.zhangqi.architecture.presenter.LoginPresenter;
import com.zhangqi.architecture.presenter.api.ILoginListener;
import com.zhangqi.architecture.util.Constant;

/**
 * Created by zhangqi on 16/11/13.
 */
public class LoginActivity extends Activity implements ILoginListener<UserInfo.UserInfoBean> {
    private EditText mLoginNameEt,mLoginPasswordEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mLoginNameEt = (EditText) findViewById(R.id.et_name);
        mLoginPasswordEt = (EditText) findViewById(R.id.et_password);
        final LoginPresenter mPresenter = new LoginPresenter(this);
        TextView login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = mLoginNameEt.getText().toString().trim();
                final String password = mLoginPasswordEt.getText().toString().trim();
                mPresenter.doLogin(name,password);
            }
        });
    }

    @Override
    public void onLoginSuccess(UserInfo.UserInfoBean userInfoBean) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(Constant.USER_NAME, userInfoBean.getUserName());
        intent.putExtra(Constant.USER_AVATAR,userInfoBean.getAvatar());
        intent.putExtra(Constant.USER_BALANCE,userInfoBean.getBalance());
        intent.putExtra(Constant.USER_ID,userInfoBean.getId());
        startActivity(intent);
    }

    public void doRegister(View view){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
