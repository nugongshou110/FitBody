package com.zhangqi.architecture.view;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpStack;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Created by zhangqi on 16/11/24.
 */
public class OkhttpStack implements HttpStack {
    @Override
    public HttpResponse performRequest(Request<?> request, Map<String, String> map) throws IOException, AuthFailureError {
        return null;
    }
}
