package com.zhangqi.architecture.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.zhangqi.architecture.R;
import com.zhangqi.architecture.adapter.PlanDetailAdapter;
import com.zhangqi.architecture.model.bean.PlanDetailModel;
import com.zhangqi.architecture.presenter.api.IPlanDetailListener;
import com.zhangqi.architecture.util.Constant;
import com.zhangqi.architecture.view.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangqi on 16/11/13.
 */
public class PlanDetailActivity extends AppCompatActivity implements IPlanDetailListener{
    private CircleImageView mAvater, mFollower1, mFollower2, mFollower3, mFollower4;
    private Button mAddGroup;
    private List<PlanDetailModel.RowsBean.DetailBean> mDatas;
    private ArrayList<PlanDetailModel.RowsBean.DetailBean> mTestDatas;
    private ListView mListView;
    private PlanDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_detail);
        initActionBar();
        initView();
        mTestDatas = new ArrayList<>();
        registerListener();
        onRequestSuccess(mDatas);
    }

    private void initView() {
        Intent intent = getIntent();
        mAvater = (CircleImageView) findViewById(R.id.avatar);
        mAddGroup = (Button) findViewById(R.id.add_group);
        mListView = (ListView) findViewById(R.id.detail_listview);
        Glide.with(this).load(intent.getStringExtra(Constant.AVATAR)).centerCrop().crossFade().into(mAvater);
        if (intent.getStringExtra(Constant.FOLLOWER_1) != null) {
            mFollower1 = (CircleImageView) findViewById(R.id.follower_1);
            Glide.with(this).load(intent.getStringExtra(Constant.FOLLOWER_1)).centerCrop().crossFade().into(mFollower1);
        }
        if (intent.getStringExtra(Constant.FOLLOWER_2) != null) {
            mFollower2 = (CircleImageView) findViewById(R.id.follower_2);
            Glide.with(this).load(intent.getStringExtra(Constant.FOLLOWER_2)).centerCrop().crossFade().into(mFollower2);
        }
        if (intent.getStringExtra(Constant.FOLLOWER_3) != null) {
            mFollower3 = (CircleImageView) findViewById(R.id.follower_3);
            Glide.with(this).load(intent.getStringExtra(Constant.FOLLOWER_3)).centerCrop().crossFade().into(mFollower3);
        }
        if (intent.getStringExtra(Constant.FOLLOWER_4) != null) {
            mFollower4 = (CircleImageView) findViewById(R.id.follower_4);
            Glide.with(this).load(intent.getStringExtra(Constant.FOLLOWER_4)).centerCrop().crossFade().into(mFollower4);
        }
    }

    private void registerListener() {
        mAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 加入群组
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
    public void onRequestSuccess(List<PlanDetailModel.RowsBean.DetailBean> data) {
        mDatas = data;
        PlanDetailModel.RowsBean.DetailBean detailBean = new PlanDetailModel.RowsBean.DetailBean();
        detailBean.setDate("2016年11月11日-2016年11月12日");
        detailBean.setDetailId("1");
        detailBean.setPlan("跑步10km");
        detailBean.setState(0);
        List<PlanDetailModel.RowsBean.DetailBean.ResultBean> resultBeanList = new ArrayList<PlanDetailModel.RowsBean.DetailBean.ResultBean>();
        PlanDetailModel.RowsBean.DetailBean.ResultBean resultBean1 = new PlanDetailModel.RowsBean.DetailBean.ResultBean();
        resultBean1.setAvatar("http://a.hiphotos.baidu.com/zhidao/pic/item/f9dcd100baa1cd11aa2ca018bf12c8fcc3ce2d74.jpg");
        resultBean1.setJudge(1);
        PlanDetailModel.RowsBean.DetailBean.ResultBean resultBean2 = new PlanDetailModel.RowsBean.DetailBean.ResultBean();
        resultBean1.setAvatar("http://a.hiphotos.baidu.com/zhidao/pic/item/f9dcd100baa1cd11aa2ca018bf12c8fcc3ce2d74.jpg");
        resultBean1.setJudge(2);
        PlanDetailModel.RowsBean.DetailBean.ResultBean resultBean3 = new PlanDetailModel.RowsBean.DetailBean.ResultBean();
        resultBean1.setAvatar("http://a.hiphotos.baidu.com/zhidao/pic/item/f9dcd100baa1cd11aa2ca018bf12c8fcc3ce2d74.jpg");
        resultBean1.setJudge(3);
        PlanDetailModel.RowsBean.DetailBean.ResultBean resultBean4 = new PlanDetailModel.RowsBean.DetailBean.ResultBean();
        resultBean1.setAvatar("http://a.hiphotos.baidu.com/zhidao/pic/item/f9dcd100baa1cd11aa2ca018bf12c8fcc3ce2d74.jpg");
        resultBean1.setJudge(1);
        resultBeanList.add(resultBean1);
        resultBeanList.add(resultBean2);
        resultBeanList.add(resultBean3);
        resultBeanList.add(resultBean4);
        detailBean.setResult(resultBeanList);
        mTestDatas.add(detailBean);
        updateListView();
    }

    private void updateListView() {
        if (mAdapter == null){
            mAdapter = new PlanDetailAdapter(this,mTestDatas);
            mListView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }
}
