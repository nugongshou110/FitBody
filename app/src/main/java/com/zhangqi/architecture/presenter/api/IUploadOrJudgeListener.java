package com.zhangqi.architecture.presenter.api;

import com.zhangqi.architecture.model.bean.EvidenceModel;

import java.util.List;

/**
 * Created by zhangqi on 16/11/27.
 */
public interface IUploadOrJudgeListener {
    void onUploadEvidenceSuccess();
    void onDisacceptSuccess();
    void onGetEvidenceSuccess(Object obj,List<EvidenceModel.RowsBean> data);
    void onNoUploadEvidence();
}
