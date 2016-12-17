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
import com.zhangqi.architecture.presenter.api.IPlanListListener;
import com.zhangqi.architecture.util.Constant;

import java.lang.ref.WeakReference;

/**
 * Created by zhangqi on 16/11/14.
 */
public class PlanListPresenter {
    private static final int UPDATE_BALANCE = 0;
    private GetDataImpl mGetDataImpl;
    private IPlanListListener mView;
    private UiHandler mHandler;

    public PlanListPresenter(IPlanListListener view) {
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
        mGetDataImpl.getData(url, new BalanceListener(this, mView), new ErrorListener());
    }

    private static class PlanListListener implements Response.Listener<String> {
        private WeakReference<PlanListPresenter> mPresenterRef;
        private WeakReference<IPlanListListener> mViewRef;

        public PlanListListener(PlanListPresenter presenter, IPlanListListener view) {
            mPresenterRef = new WeakReference<PlanListPresenter>(presenter);
            mViewRef = new WeakReference<IPlanListListener>(view);
        }

        @Override
        public void onResponse(String s) {
            Log.i("zhangqiaaa","get planList response = "+s);
            PlanListPresenter presenter = mPresenterRef.get();
            IPlanListListener view = mViewRef.get();
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
        private WeakReference<PlanListPresenter> mPresenterRef;
        private WeakReference<IPlanListListener> mViewRef;

        public BalanceListener(PlanListPresenter presenter, IPlanListListener view) {
            mPresenterRef = new WeakReference<PlanListPresenter>(presenter);
            mViewRef = new WeakReference<IPlanListListener>(view);
        }

        @Override
        public void onResponse(String s) {
            PlanListPresenter presenter = mPresenterRef.get();
            IPlanListListener view = mViewRef.get();
            if (presenter != null && view != null) {
                Gson gson = new Gson();
                UserBalance userBalance = gson.fromJson(s, UserBalance.class);
                if (userBalance != null && userBalance.getCode() == 0) {
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
        private WeakReference<PlanListPresenter> mPresenterRef;
        private WeakReference<IPlanListListener> mViewRef;

        public UiHandler(PlanListPresenter presenter,IPlanListListener view) {
            mPresenterRef = new WeakReference<PlanListPresenter>(presenter);
            mViewRef = new WeakReference<IPlanListListener>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            PlanListPresenter planListPresenter = mPresenterRef.get();
            IPlanListListener view = mViewRef.get();
            if (planListPresenter != null && view != null) {
                switch (msg.what) {
                    case UPDATE_BALANCE:
                        planListPresenter.getUserBalance(view.onGetUserId());
                        break;
                }
            }
            super.handleMessage(msg);
        }
    }
}
