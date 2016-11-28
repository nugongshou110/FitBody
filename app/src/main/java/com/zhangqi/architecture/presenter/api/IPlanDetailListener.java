package com.zhangqi.architecture.presenter.api;

import com.zhangqi.architecture.model.bean.PlanDetailModel;

import java.util.List;

/**
 * Created by zhangqi on 16/11/20.
 */
public interface IPlanDetailListener {
    void onRequestPlanDetailSuccess(List<PlanDetailModel.RowsBean> data,String planName);

    void onAddGroupSuccess();
}
