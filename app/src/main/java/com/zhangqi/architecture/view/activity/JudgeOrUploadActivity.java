package com.zhangqi.architecture.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhangqi.architecture.R;
import com.zhangqi.architecture.app.AppController;
import com.zhangqi.architecture.model.bean.EvidenceModel;
import com.zhangqi.architecture.presenter.UploadOrJudgePresenter;
import com.zhangqi.architecture.presenter.api.IUploadOrJudgeListener;
import com.zhangqi.architecture.util.Constant;
import com.zhangqi.architecture.view.widget.CircleImageView;

import java.util.List;

/**
 * Created by zhangqi on 16/11/24.
 */
public class JudgeOrUploadActivity extends AppCompatActivity implements IUploadOrJudgeListener {
    private TextView mTvNotify;
    private ImageView mIvEvidence;
    private TextView mDate;
    private TextView mPlanDetail;
    private ImageView mJudgeResult;
    private int mPlanId;
    private String mEvidencePhotoUrl;
    private List<EvidenceModel.RowsBean> mDisacceptDatas;
    private String mCommentStr;
    private ImageView mIvDisaccept;
    private boolean mIsSelf;
    private UploadOrJudgePresenter mPresenter;
    private String mPicturePath;
    private CircleImageView mAvatar1, mAvatar2, mAvatar3, mAvatar4;
    private TextView mComment1, mComment2, mComment3, mComment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_judge);
        initActivityBar();
        mEvidencePhotoUrl = getIntent().getStringExtra(Constant.EVIDENCE_PHOTO);
        mPresenter = new UploadOrJudgePresenter(this);
        mIsSelf = getIntent().getBooleanExtra(Constant.IS_SELF, false);
        Log.i("zhangqiddd","isSelf = "+mIsSelf);
        mTvNotify = (TextView) findViewById(R.id.tv_notify);
        mIvEvidence = (ImageView) findViewById(R.id.iv_evidence);
        mJudgeResult = (ImageView) findViewById(R.id.judge_result);
        mDate = (TextView) findViewById(R.id.date);
        mPlanDetail = (TextView) findViewById(R.id.plan_detail);
        mIvDisaccept = (ImageView) findViewById(R.id.iv_disaccept);
        mAvatar1 = (CircleImageView) findViewById(R.id.disaccept_avatar_1);
        mAvatar2 = (CircleImageView) findViewById(R.id.disaccept_avatar_2);
        mAvatar3 = (CircleImageView) findViewById(R.id.disaccept_avatar_3);
        mAvatar4 = (CircleImageView) findViewById(R.id.disaccept_avatar_4);
        mComment1 = (TextView) findViewById(R.id.disaccept_comment_1);
        mComment2 = (TextView) findViewById(R.id.disaccept_comment_2);
        mComment3 = (TextView) findViewById(R.id.disaccept_comment_3);
        mComment4 = (TextView) findViewById(R.id.disaccept_comment_4);
        if (TextUtils.isEmpty(mEvidencePhotoUrl)) {
            if (mIsSelf) {
                mTvNotify.setText("点击上传图片");
                mIvDisaccept.setVisibility(View.GONE);
            } else {
                mTvNotify.setText("还没有上传图片");
                mIvDisaccept.setVisibility(View.VISIBLE);
            }
        } else {
            mTvNotify.setVisibility(View.GONE);
            Glide.with(this).load(mEvidencePhotoUrl).centerCrop().crossFade().into(mIvEvidence);
            if (mIsSelf){
                mIvDisaccept.setVisibility(View.GONE);
            }else{
                mIvDisaccept.setVisibility(View.VISIBLE);
            }
        }
        mDate.setText(getIntent().getStringExtra(Constant.DATE));
        mPlanDetail.setText(getIntent().getStringExtra(Constant.PLAN_DETAIL));
        mPlanId = getIntent().getIntExtra(Constant.PLAN_ID, -1);

        if (mIsSelf) {
            mIvEvidence.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, Constant.RESULT_LOAD_IMAGE);
                }
            });
        } else {
            mIvDisaccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCommentDialog();
                }
            });
        }
        mPresenter.doGetEvidence(mPlanId);
    }

    private void showCommentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_comment, null);
        final EditText commentEt = (EditText) view.findViewById(R.id.et_comment);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCommentStr = commentEt.getText().toString();
                mPresenter.doJudge(getIntent().getIntExtra(Constant.USER_ID, -1), mPlanId, 1, mCommentStr);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mPicturePath = cursor.getString(columnIndex);
            cursor.close();
            mPresenter.uploadEvidence(mPlanId, mPicturePath);
        }
    }

    private void initActivityBar() {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            if (mIsSelf) {
                supportActionBar.setTitle("上传照片");
            } else {
                supportActionBar.setTitle("评价");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(JudgeOrUploadActivity.this, PlanDetailActivity.class);
                intent.putExtra(Constant.PLAN_ID, mPlanId);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUploadEvidenceSuccess() {
        // String picturePath contains the path of selected Image
        Glide.with(this).load(mPicturePath).centerCrop().crossFade().into(mIvEvidence);
        Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
        mTvNotify.setVisibility(View.GONE);
        mIvDisaccept.setVisibility(View.GONE);
    }

    @Override
    public void onDisacceptSuccess() {
        mTvNotify.setVisibility(View.GONE);
        mIvDisaccept.setVisibility(View.GONE);
        mJudgeResult.setVisibility(View.VISIBLE);
        EvidenceModel.RowsBean rowsBean = new EvidenceModel.RowsBean();
        rowsBean.setAvatar(AppController.getInstance().getUserInfo().getAvatar());
        rowsBean.setComment(mCommentStr);
        mDisacceptDatas.add(rowsBean);
        refreshComment(mDisacceptDatas);
    }

    @Override
    public void onGetEvidenceSuccess(List<EvidenceModel.RowsBean> data) {
        if (data != null) {
            mDisacceptDatas = data;
            for (int i = 0; i < data.size(); i++) {
                EvidenceModel.RowsBean rowsBean = data.get(i);
                if (getIntent().getIntExtra(Constant.USER_ID, -1) == rowsBean.getId()) {
                    mIvDisaccept.setVisibility(View.GONE);
                }
            }
            refreshComment(data);
        }
    }

    private void refreshComment(List<EvidenceModel.RowsBean> data) {
        try {
            if (data.get(0) != null) {
                Glide.with(this).load(Constant.AVATAR_PREFIX + data.get(0).getAvatar()).crossFade().centerCrop().into(mAvatar1);
                mComment1.setText(data.get(0).getComment());
            }
            if (data.get(1) != null) {
                Glide.with(this).load(Constant.AVATAR_PREFIX + data.get(1).getAvatar()).crossFade().centerCrop().into(mAvatar2);
                mComment2.setText(data.get(1).getComment());
            }
            if (data.get(2) != null) {
                Glide.with(this).load(Constant.AVATAR_PREFIX + data.get(2).getAvatar()).crossFade().centerCrop().into(mAvatar3);
                mComment3.setText(data.get(2).getComment());
            }
            if (data.get(3) != null) {
                Glide.with(this).load(Constant.AVATAR_PREFIX + data.get(3).getAvatar()).crossFade().centerCrop().into(mAvatar4);
                mComment4.setText(data.get(3).getComment());
            }
        } catch (Exception e) {

        }
    }
}
