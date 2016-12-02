package com.zhangqi.architecture.presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.zhangqi.architecture.model.api.IGetDataListener;
import com.zhangqi.architecture.model.bean.EvidenceModel;
import com.zhangqi.architecture.model.bean.EvidenceNoComment;
import com.zhangqi.architecture.model.bean.JudgeModel;
import com.zhangqi.architecture.model.bean.UploadEvidenceModel;
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

    public void uploadEvidence(final int planId, final String picturePath) {
        UploadEvidenceModel model = new UploadEvidenceModel();
        model.setPlanItemId(planId);
        Gson gson = new Gson();
        final String s = gson.toJson(model);
        final String url = "http://" + Constant.IP + ":8080/arc/plan/addEvidence";
        Log.i("zhangqieee","upload evidence planId = "+planId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                UploadPhoto.getInstance(null).doUpload(url, s, picturePath);
            }
        }).start();

    }

    public void doJudge(int userId, int planId, int judge, final String comment) {
        JudgeModel judgeModel = new JudgeModel();
        judgeModel.setUserId(userId);
        judgeModel.setPlanItemId(planId);
        judgeModel.setComment(comment);
        judgeModel.setJudge(1);
        Gson gson = new Gson();
        String msg = gson.toJson(judgeModel);
        String url = "http://" + Constant.IP + ":8080/arc/judge/judge?message="+msg;
        Log.i("zhangqiddd","judge url = "+url);
        mGetDataImpl.getData(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int code = (int) jsonObject.get("code");
                    Log.i("zhangqiddd","judge code = "+code);
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
                Log.i("zhangqieee","get evidence = "+s);
                if (!s.contains("obj")) {
                    mView.onNoUploadEvidence();
                }else if (!s.contains("rows")){
                    Log.e("zhangqieee","no rows");
                    Gson gson = new Gson();
                    EvidenceNoComment evidenceNoComment = gson.fromJson(s, EvidenceNoComment.class);
                    if (evidenceNoComment != null && evidenceNoComment.getCode() == 0) {
                        mView.onGetEvidenceSuccess(evidenceNoComment.getObj(), null);
                    }
                }

                else {
                    Gson gson = new Gson();
                    EvidenceModel evidenceModel = gson.fromJson(s, EvidenceModel.class);
                    if (evidenceModel != null && evidenceModel.getCode() == 0) {
                        mView.onGetEvidenceSuccess(evidenceModel.getObj(), evidenceModel.getRows());
                    }
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
