package com.zhangqi.architecture.view.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhangqi.architecture.R;
import com.zhangqi.architecture.presenter.UploadOrJudgePresenter;
import com.zhangqi.architecture.presenter.api.IUploadOrJudgeListener;
import com.zhangqi.architecture.util.Constant;

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
    private ImageView mIvDisaccept;
    private boolean mIsSelf;
    private UploadOrJudgePresenter mPresenter;
    private String mPicturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_judge);
        initActivityBar();
        mEvidencePhotoUrl = getIntent().getStringExtra(Constant.EVIDENCE_PHOTO);
        mPresenter = new UploadOrJudgePresenter(this);
        mIsSelf = getIntent().getBooleanExtra(Constant.IS_SELF, false);
        mTvNotify = (TextView) findViewById(R.id.tv_notify);
        mIvEvidence = (ImageView) findViewById(R.id.iv_evidence);
        mJudgeResult = (ImageView) findViewById(R.id.judge_result);
        mDate = (TextView) findViewById(R.id.date);
        mPlanDetail = (TextView) findViewById(R.id.plan_detail);
        mIvDisaccept = (ImageView) findViewById(R.id.iv_disaccept);
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
        }else{
            mIvDisaccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.doJudge(getIntent().getIntExtra(Constant.USER_ID,-1),mPlanId,1);
                }
            });
        }
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
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(JudgeOrUploadActivity.this,PlanDetailActivity.class);
                intent.putExtra(Constant.PLAN_ID,mPlanId);
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
    }
}
