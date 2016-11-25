package com.zhangqi.architecture.presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zhangqi.architecture.app.AppController;
import com.zhangqi.architecture.model.api.IGetDataListener;
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
        String url = "http://"+ Constant.IP+":8080/arc/user/login?message={\"userName\":"+name+",\"password\":"+password+"}";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                mView.onLoginSuccess(s);
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
