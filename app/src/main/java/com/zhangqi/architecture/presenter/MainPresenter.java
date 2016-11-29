package com.zhangqi.architecture.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.zhangqi.architecture.model.bean.PlanListModel;
import com.zhangqi.architecture.model.bean.UserBalance;
import com.zhangqi.architecture.model.engine.GetDataImpl;
import com.zhangqi.architecture.presenter.api.IMainViewListener;
import com.zhangqi.architecture.util.Constant;

import java.lang.ref.WeakReference;

/**
 * Created by zhangqi on 16/11/14.
 */
public class MainPresenter {
    private static final int UPDATE_BALANCE = 0;
    private GetDataImpl mGetDataImpl;
    private IMainViewListener mView;
    private UiHandler mHandler;

    public MainPresenter(IMainViewListener view) {
        mGetDataImpl = new GetDataImpl();
        mView = view;
        mHandler = new UiHandler(this,mView);
    }

    public void getPlanList() {
        //TODO getPlanList
        String url = "http://" + Constant.IP + ":8080/arc/plan/getPlanList";
        Log.i("zhangqibbb","getPlanList url = "+url);
        mGetDataImpl.getData(url, new PlanListListener(this, mView), new ErrorListener());
    }

    public void getUserBalance(String userId) {

        String url = "http://" + Constant.IP + ":8080/arc/user/geBalance?userId="+userId;
        Log.i("zhangqiaaa","getUserBanlance url = "+url);
        mGetDataImpl.getData(url, new BalanceListener(this, mView), new ErrorListener());
    }

    private static class PlanListListener implements Response.Listener<String> {
        private WeakReference<MainPresenter> mPresenterRef;
        private WeakReference<IMainViewListener> mViewRef;

        public PlanListListener(MainPresenter presenter, IMainViewListener view) {
            mPresenterRef = new WeakReference<MainPresenter>(presenter);
            mViewRef = new WeakReference<IMainViewListener>(view);
        }

        @Override
        public void onResponse(String s) {
            Log.i("zhangqibbb","get planList response = "+s);
            MainPresenter presenter = mPresenterRef.get();
            IMainViewListener view = mViewRef.get();
            if (presenter != null && view != null) {
                Gson gson = new Gson();
                PlanListModel planListModel = gson.fromJson(s, PlanListModel.class);
                if (planListModel != null && planListModel.getCode() == 0) {
                    view.onRequestSuccess(planListModel.getRows());
                    presenter.mHandler.removeCallbacksAndMessages(null);
                    presenter.mHandler.sendEmptyMessage(UPDATE_BALANCE);
                }
            }
        }
    }

    private static class BalanceListener implements Response.Listener<String> {
        private WeakReference<MainPresenter> mPresenterRef;
        private WeakReference<IMainViewListener> mViewRef;

        public BalanceListener(MainPresenter presenter, IMainViewListener view) {
            mPresenterRef = new WeakReference<MainPresenter>(presenter);
            mViewRef = new WeakReference<IMainViewListener>(view);
        }

        @Override
        public void onResponse(String s) {
            MainPresenter presenter = mPresenterRef.get();
            IMainViewListener view = mViewRef.get();
            if (presenter != null && view != null) {
                Gson gson = new Gson();
                UserBalance userBalance = gson.fromJson(s, UserBalance.class);
                if (userBalance != null && userBalance.getCode() == 0) {
                    Log.i("zhangqiaaa","BalanceListener getBanlance = "+userBalance.getObj().getBalance());
                    int balance = userBalance.getObj().getBalance();
                    view.onUpdateUserBalance(String.valueOf(balance));
                }
                presenter.mHandler.sendEmptyMessageDelayed(UPDATE_BALANCE, 5000);
            }
        }
    }

    private static class ErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.i("zhangqi", "VolleyError = " + volleyError);
        }
    }

    private static class UiHandler extends Handler {
        private WeakReference<MainPresenter> mPresenterRef;
        private WeakReference<IMainViewListener> mViewRef;

        public UiHandler(MainPresenter presenter,IMainViewListener view) {
            mPresenterRef = new WeakReference<MainPresenter>(presenter);
            mViewRef = new WeakReference<IMainViewListener>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            MainPresenter mainPresenter = mPresenterRef.get();
            IMainViewListener view = mViewRef.get();
            if (mainPresenter != null && view != null) {
                switch (msg.what) {
                    case UPDATE_BALANCE:
                        mainPresenter.getUserBalance(view.onGetUserId());
                        break;
                }
            }
            super.handleMessage(msg);
        }
    }
}
