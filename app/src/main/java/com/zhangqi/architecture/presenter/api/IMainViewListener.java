package com.zhangqi.architecture.presenter.api;

import com.zhangqi.architecture.model.bean.PlanListModel;

import java.util.List;

/**
 * Created by zhangqi on 16/11/14.
 */
public interface IMainViewListener {
    void onRequestSuccess(List<PlanListModel.RowsBean> data);
}
