package com.zhangqi.architecture.model.api;

import com.android.volley.Response;

/**
 * Created by zhangqi on 16/11/17.
 */
public interface IGetDataListener {
    void getData(String url,Response.Listener<String> responseListener,
                 Response.ErrorListener errorListener);
}
