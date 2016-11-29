package com.zhangqi.architecture.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.zhangqi.architecture.model.api.IGetDataListener;
import com.zhangqi.architecture.model.bean.EvidenceModel;
import com.zhangqi.architecture.model.bean.JudgeModel;
import com.zhangqi.architecture.model.engine.GetDataImpl;
import com.zhangqi.architecture.presenter.api.IUploadOrJudgeListener;
import com.zhangqi.architecture.presenter.api.IUploadPictureListener;
import com.zhangqi.architecture.util.Constant;
import com.zhangqi.architecture.util.UploadPhoto;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangqi on 16/11/27.
 */
public class UploadOrJudgePresenter implements IUploadPictureListener {
    private IGetDataListener mGetDataImpl;
    private IUploadOrJudgeListener mView;

    public UploadOrJudgePresenter(IUploadOrJudgeListener view) {
        mGetDataImpl = new GetDataImpl();
        mView = view;
    }

    public void uploadEvidence(int planId, String picturePath) {
        String url = "http://" + Constant.IP + ":8080/arc/plan/addEvidence";
        UploadPhoto.getInstance(null).doUpload(url, "\"planItemId\":" + planId, picturePath);
    }

    public void doJudge(int userId, int planId, int judge, String comment) {
        JudgeModel judgeModel = new JudgeModel();
        judgeModel.setUserId(userId);
        judgeModel.setPlanItemId(planId);
        judgeModel.setComment(comment);
        Gson gson = new Gson();
        String msg = gson.toJson(judgeModel);
        String url = "http://" + Constant.IP + ":8080/arc/judge/judge?message="+msg;
        mGetDataImpl.getData(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int code = (int) jsonObject.get("code");
                    if (code == 0) {
                        mView.onDisacceptSuccess();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }

    public void doGetEvidence(int planId) {
        String url = "http://" + Constant.IP + ":8080/arc/plan/getEvidence?message={\"planItemId\":" + planId + "}";
        mGetDataImpl.getData(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                EvidenceModel evidenceModel = gson.fromJson(s, EvidenceModel.class);
                if (evidenceModel != null && evidenceModel.getCode() == 0) {
                    mView.onGetEvidenceSuccess(evidenceModel.getRows());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

    }


    @Override
    public void onUploadSuccess() {
        mView.onUploadEvidenceSuccess();
    }
}
