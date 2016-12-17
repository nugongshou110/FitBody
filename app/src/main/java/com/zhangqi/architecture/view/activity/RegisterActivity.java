package com.zhangqi.architecture.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhangqi.architecture.R;
import com.zhangqi.architecture.model.bean.RegisterClientModel;
import com.zhangqi.architecture.model.bean.UserInfo;
import com.zhangqi.architecture.presenter.LoginPresenter;
import com.zhangqi.architecture.presenter.api.ILoginListener;
import com.zhangqi.architecture.presenter.api.IUploadPictureListener;
import com.zhangqi.architecture.util.Constant;
import com.zhangqi.architecture.util.UploadPhoto;
import com.zhangqi.architecture.view.widget.CircleImageView;

/**
 * Created by zhangqi on 16/11/25.
 */
public class RegisterActivity extends AppCompatActivity implements IUploadPictureListener, ILoginListener<UserInfo.UserInfoBean> {
    private EditText mUserNameEt;
    private EditText mUserPasswordEt;
    private CircleImageView mUploadAcatar;
    private TextView mRegister, mLogin;
    private String mAvatarPath;
    private String mUserNameString;
    private String mUserPasswordString;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initActivityBar();
        mUserNameEt = (EditText) findViewById(R.id.et_username);
        mUserPasswordEt = (EditText) findViewById(R.id.et_password);
        mUploadAcatar = (CircleImageView) findViewById(R.id.upload_avatar);
        mRegister = (TextView) findViewById(R.id.register);
        mLogin = (TextView) findViewById(R.id.login);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在注册...");
        //从图库中取头像并且显示出来
        mUploadAcatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Constant.RESULT_LOAD_IMAGE);
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                register();
            }
        });
    }

    private void initActivityBar() {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(false);
            supportActionBar.setDisplayShowTitleEnabled(true);
            supportActionBar.setTitle("注册");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            mAvatarPath = picturePath;
            cursor.close();
            // String picturePath contains the path of selected Image
            Glide.with(this).load(picturePath).centerCrop().crossFade().into(mUploadAcatar);
        }
    }

    private void register() {
        final String url = "http://" + Constant.IP + ":8080/arc/user/register";
        Log.i("zhangqiaaa", "register url = " + url);
        mUserNameString = mUserNameEt.getText().toString().trim();
        mUserPasswordString = mUserPasswordEt.getText().toString().trim();
        RegisterClientModel registerClientModel = new RegisterClientModel();
        registerClientModel.setUserName(mUserNameString);
        registerClientModel.setPassword(mUserPasswordString);
        Gson gson = new Gson();
        final String msg = gson.toJson(registerClientModel);
        new Thread(new Runnable() {
            @Override
            public void run() {
                UploadPhoto.getInstance(RegisterActivity.this).doUpload(url, msg, mAvatarPath);
            }
        }).start();

    }

    @Override
    public void onUploadSuccess() {
        LoginPresenter presenter = new LoginPresenter(this);
        presenter.doLogin(mUserNameString, mUserPasswordString);
    }

    @Override
    public void onLoginSuccess(UserInfo.UserInfoBean userInfoBean) {
        mProgressDialog.dismiss();
        Log.i("zhangqiaaa","onLoginSuccess");
        Intent intent = new Intent(RegisterActivity.this, PlanListActivity.class);
        intent.putExtra(Constant.USER_NAME, userInfoBean.getUserName());
        intent.putExtra(Constant.USER_AVATAR, userInfoBean.getAvatar());
        intent.putExtra(Constant.USER_BALANCE, userInfoBean.getBalance());
        intent.putExtra(Constant.USER_ID, userInfoBean.getId());
        startActivity(intent);
    }
}
