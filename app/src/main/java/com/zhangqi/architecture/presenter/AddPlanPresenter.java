package com.zhangqi.architecture.presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.zhangqi.architecture.app.AppController;
import com.zhangqi.architecture.model.bean.AddPlanModel;
import com.zhangqi.architecture.util.Constant;

/**
 * Created by zhangqi on 16/11/27.
 */
public class AddPlanPresenter {
    public void doAddPlan(int userId, String startDate, String endDate, String cash, String planDetail) {
        AddPlanModel addPlanModel = new AddPlanModel();
        addPlanModel.setStartTime(startDate);
        addPlanModel.setEndTime(endDate);
        addPlanModel.setMoney(cash);
        addPlanModel.setPlanName(planDetail);
        addPlanModel.setUserId(userId);
        Gson gson = new Gson();
        String message = gson.toJson(addPlanModel);
        String url = "http://" + Constant.IP + ":8080/arc/plan/addPlan/?message=" + message;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("zhangqiaaa", "error = " + volleyError);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}
