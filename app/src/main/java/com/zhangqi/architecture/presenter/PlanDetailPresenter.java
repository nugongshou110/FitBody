package com.zhangqi.architecture.presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.zhangqi.architecture.model.bean.PlanDetailModel;
import com.zhangqi.architecture.model.engine.GetDataImpl;
import com.zhangqi.architecture.presenter.api.IPlanDetailListener;

import java.lang.ref.WeakReference;

/**
 * Created by zhangqi on 16/11/14.
 */
public class PlanDetailPresenter {
    private GetDataImpl mGetDataImpl;
    private IPlanDetailListener mView;
    public PlanDetailPresenter(IPlanDetailListener view){
        mGetDataImpl = new GetDataImpl();
        mView = view;
    }

    public void requestServer(){
        //TODO getPlanList
        String url = "";
        mGetDataImpl.getData(url,new ResponseListener(this,mView),new ErrorListener());
    }

    private static class ResponseListener implements Response.Listener<String>{
        private WeakReference<PlanDetailPresenter> mPresenterRef;
        private WeakReference<IPlanDetailListener> mViewRef;

        public ResponseListener(PlanDetailPresenter presenter, IPlanDetailListener view){
            mPresenterRef = new WeakReference<PlanDetailPresenter>(presenter);
            mViewRef = new WeakReference<IPlanDetailListener>(view);
        }

        @Override
        public void onResponse(String s) {
            PlanDetailPresenter presenter = mPresenterRef.get();
            IPlanDetailListener view = mViewRef.get();
            if (presenter != null && view != null){
                Gson gson = new Gson();
                PlanDetailModel planDetailModel = gson.fromJson(s, PlanDetailModel.class);
                if (planDetailModel != null && "0".equals(planDetailModel.getCode())){
                    view.onRequestSuccess(planDetailModel.getRows().getDetail());
                }
            }
        }
    }

    private static class ErrorListener implements Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.i("zhangqi","VolleyError = "+volleyError);
        }
    }
}
