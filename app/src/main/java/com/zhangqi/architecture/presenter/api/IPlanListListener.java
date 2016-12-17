package com.zhangqi.architecture.presenter.api;

import android.content.Context;

import com.zhangqi.architecture.model.bean.PlanListModel;

import java.util.List;

/**
 * Created by zhangqi on 16/11/14.
 */
public interface IPlanListListener {
    void onRequestSuccess(List<PlanListModel.RowsBean> data);
    Context onGetContext();
    void onUpdateUserBalance(String balance);
    String onGetUserId();

}
