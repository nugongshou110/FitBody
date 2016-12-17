package com.zhangqi.architecture.presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.zhangqi.architecture.app.AppController;
import com.zhangqi.architecture.model.api.IGetDataListener;
import com.zhangqi.architecture.model.bean.RegisterClientModel;
import com.zhangqi.architecture.model.bean.UserInfo;
import com.zhangqi.architecture.model.engine.GetDataImpl;
import com.zhangqi.architecture.presenter.api.ILoginListener;
import com.zhangqi.architecture.util.Constant;

/**
 * Created by zhangqi on 16/11/25.
 */
public class LoginPresenter {
    private IGetDataListener mGetDataImpl;
    private ILoginListener mView;
    public LoginPresenter(ILoginListener view){
        mGetDataImpl = new GetDataImpl();
        mView = view;
    }
    public void doLogin(String name,String password){
        Log.i("zhangqiaaa","LoginPresenter doLogin name = "+name+" password = "+password);
        RegisterClientModel registerClientModel = new RegisterClientModel();
        registerClientModel.setUserName(name);
        registerClientModel.setPassword(password);
        Gson gson = new Gson();
        String msg = gson.toJson(registerClientModel);
        String url = "http://"+ Constant.IP+":8080/arc/user/login?message="+msg;
        Log.i("zhangqiaaa","login url = "+url);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                Log.i("zhangqifff","login = "+s);
                UserInfo userInfo = gson.fromJson(s, UserInfo.class);
                if (userInfo.getCode() == 0){
                    if (userInfo.getObj() != null){
                        mView.onLoginSuccess(userInfo.getObj());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("zhangqiaaa","error = "+volleyError);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}
