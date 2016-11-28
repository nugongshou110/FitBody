package com.zhangqi.architecture.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhangqi.architecture.R;
import com.zhangqi.architecture.adapter.PlanDetailAdapter;
import com.zhangqi.architecture.model.bean.PlanDetailModel;
import com.zhangqi.architecture.presenter.PlanDetailPresenter;
import com.zhangqi.architecture.presenter.api.IPlanDetailListener;
import com.zhangqi.architecture.util.Constant;
import com.zhangqi.architecture.view.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangqi on 16/11/13.
 */
public class PlanDetailActivity extends AppCompatActivity implements IPlanDetailListener {
    private CircleImageView mAvater, mFollower1, mFollower2, mFollower3, mFollower4;
    private TextView mAddGroup;
    private List<PlanDetailModel.RowsBean> mDatas;
    private ListView mListView;
    private ArrayList<String> mSupervisors;
    private PlanDetailAdapter mAdapter;
    private PlanDetailPresenter mPresenter;
    private String mPlanName;
    private boolean mIsSelfPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_detail);
        initActionBar();
        initView();
        mSupervisors = new ArrayList<String>();
        mPresenter = new PlanDetailPresenter(this);
        registerListener();
        int planId = getIntent().getIntExtra(Constant.PLAN_ID, -1);
        mPresenter.requestPlanDetail(planId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int planId = intent.getIntExtra(Constant.PLAN_ID, -1);
        mPresenter.requestPlanDetail(planId);
    }

    private void initView() {
        Intent intent = getIntent();
        mAvater = (CircleImageView) findViewById(R.id.avatar);
        mAddGroup = (TextView) findViewById(R.id.add_group);
        mFollower1 = (CircleImageView) findViewById(R.id.follower_1);
        mFollower2 = (CircleImageView) findViewById(R.id.follower_2);
        mFollower3 = (CircleImageView) findViewById(R.id.follower_3);
        mFollower4 = (CircleImageView) findViewById(R.id.follower_4);
        mListView = (ListView) findViewById(R.id.detail_listview);
        Glide.with(this).load(intent.getStringExtra(Constant.USER_AVATAR)).centerCrop().crossFade().into(mAvater);
        initSupervisors(intent);
        refreshSupervisors();
        mIsSelfPlan = intent.getBooleanExtra(Constant.DISMISS_ADD_GROUP, false);
        if (mIsSelfPlan) {
            mAddGroup.setVisibility(View.GONE);
        } else {
            mAddGroup.setVisibility(View.VISIBLE);
        }

    }

    private void initSupervisors(Intent intent) {
        if (intent.getStringExtra(Constant.FOLLOWER_1) != null) {
            mSupervisors.add(intent.getStringExtra(Constant.FOLLOWER_1));
        }
        if (intent.getStringExtra(Constant.FOLLOWER_2) != null) {
            mSupervisors.add(intent.getStringExtra(Constant.FOLLOWER_2));
        }
        if (intent.getStringExtra(Constant.FOLLOWER_3) != null) {
            mSupervisors.add(intent.getStringExtra(Constant.FOLLOWER_3));
        }
        if (intent.getStringExtra(Constant.FOLLOWER_4) != null) {
            mSupervisors.add(intent.getStringExtra(Constant.FOLLOWER_4));
        }
    }

    private void refreshSupervisors() {
        try {
            if (mSupervisors.get(0) != null) {
                Glide.with(this).load(mSupervisors.get(0)).centerCrop().crossFade().into(mFollower1);
            }
            if (mSupervisors.get(1) != null) {
                Glide.with(this).load(mSupervisors.get(1)).centerCrop().crossFade().into(mFollower2);
            }
            if (mSupervisors.get(2) != null) {
                Glide.with(this).load(mSupervisors.get(2)).centerCrop().crossFade().into(mFollower3);
            }
            if (mSupervisors.get(3) != null) {
                Glide.with(this).load(mSupervisors.get(3)).centerCrop().crossFade().into(mFollower4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSupervisors() {
        mSupervisors.add(getIntent().getStringExtra(Constant.AVATAR_SELF));
    }

    private void registerListener() {
        mAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 加入群组
                int planId = getIntent().getIntExtra(Constant.PLAN_ID, -1);
                int userId = getIntent().getIntExtra(Constant.USER_ID, -1);
                mPresenter.addGroup(planId, userId);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlanDetailModel.RowsBean bean = mDatas.get(position);
                Intent intent = new Intent(PlanDetailActivity.this, JudgeOrUploadActivity.class);
                intent.putExtra(Constant.DATE,bean.getPlanDate());
                intent.putExtra(Constant.PLAN_DETAIL,mPlanName);
                intent.putExtra(Constant.PLAN_ID,bean.getId());
                intent.putExtra(Constant.USER_ID,getIntent().getIntExtra(Constant.USER_ID,-1));
                intent.putExtra(Constant.EVIDENCE_PHOTO, Constant.AVATAR_PREFIX + bean.getEvidencePhoto());
                intent.putExtra(Constant.IS_SELF, mIsSelfPlan);
                startActivity(intent);
            }
        });
    }

    private void initActionBar() {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            Intent intent = getIntent();
            if (intent != null) {
                supportActionBar.setTitle(intent.getStringExtra(Constant.TITLE));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPlanDetailSuccess(List<PlanDetailModel.RowsBean> data, String planName) {
        mDatas = data;
        updateListView(data, planName);
    }

    @Override
    public void onAddGroupSuccess() {
        Toast.makeText(this, "监督成功", Toast.LENGTH_SHORT).show();
        updateSupervisors();
        refreshSupervisors();
        mAddGroup.setClickable(false);
    }

    private void updateListView(List<PlanDetailModel.RowsBean> data, String planName) {
        mPlanName = planName;
        if (mAdapter == null) {
            mAdapter = new PlanDetailAdapter(this, data, planName);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
