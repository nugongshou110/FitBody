package com.zhangqi.architecture.presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.zhangqi.architecture.model.bean.PlanListModel;
import com.zhangqi.architecture.model.engine.GetDataImpl;
import com.zhangqi.architecture.presenter.api.IMainViewListener;

import java.lang.ref.WeakReference;

/**
 * Created by zhangqi on 16/11/14.
 */
public class MainPresenter {
    private GetDataImpl mGetDataImpl;
    private IMainViewListener mView;
    public MainPresenter(IMainViewListener view){
        mGetDataImpl = new GetDataImpl();
        mView = view;
    }

    public void requestServer(){
        //TODO getPlanList
        String url = "";
        mGetDataImpl.getData(url,new ResponseListener(this,mView),new ErrorListener());
    }

    private static class ResponseListener implements Response.Listener<String>{
        private WeakReference<MainPresenter> mPresenterRef;
        private WeakReference<IMainViewListener> mViewRef;

        public ResponseListener(MainPresenter presenter, IMainViewListener view){
            mPresenterRef = new WeakReference<MainPresenter>(presenter);
            mViewRef = new WeakReference<IMainViewListener>(view);
        }

        @Override
        public void onResponse(String s) {
            MainPresenter presenter = mPresenterRef.get();
            IMainViewListener view = mViewRef.get();
            if (presenter != null && view != null){
                Gson gson = new Gson();
                PlanListModel planListModel = gson.fromJson(s, PlanListModel.class);
                if (planListModel != null && "0".equals(planListModel.getCode())){
                    view.onRequestSuccess(planListModel.getRows());
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
