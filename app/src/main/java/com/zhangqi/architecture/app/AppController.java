package com.zhangqi.architecture.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.zhangqi.architecture.model.bean.UserInfo;

/**
 * Created by zhangqi on 16/11/17.
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private UserInfo.UserInfoBean mUserInfo;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public void setUserInfo(String userName, int userId, int userBalance, String userAvatar) {
        if (mUserInfo == null) {
            mUserInfo = new UserInfo.UserInfoBean();
        }
        mUserInfo.setUserName(userName);
        mUserInfo.setAvatar(userAvatar);
        mUserInfo.setId(userId);
        mUserInfo.setBalance(userBalance);
    }

    public UserInfo.UserInfoBean getUserInfo() {
        if (mUserInfo == null) {
            return null;
        } else {
            return mUserInfo;
        }
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
