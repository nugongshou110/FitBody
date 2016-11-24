package com.zhangqi.architecture.model.engine;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.zhangqi.architecture.app.AppController;
import com.zhangqi.architecture.model.api.IGetDataListener;

/**
 * Created by zhangqi on 16/11/17.
 */
public class GetDataImpl implements IGetDataListener {
    @Override
    public void getData(String url,Response.Listener<String> responseListener,
                        Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(url,responseListener,errorListener);
        AppController.getInstance().addToRequestQueue(request);
    }
}
