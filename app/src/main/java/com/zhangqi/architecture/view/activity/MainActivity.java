package com.zhangqi.architecture.view.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhangqi.architecture.R;
import com.zhangqi.architecture.adapter.PlanListAdapter;
import com.zhangqi.architecture.adapter.api.ICardViewListener;
import com.zhangqi.architecture.app.AppController;
import com.zhangqi.architecture.model.bean.PlanListModel;
import com.zhangqi.architecture.model.bean.UserInfo;
import com.zhangqi.architecture.presenter.MainPresenter;
import com.zhangqi.architecture.presenter.api.IMainViewListener;
import com.zhangqi.architecture.util.Constant;
import com.zhangqi.architecture.view.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, IMainViewListener, ICardViewListener {
    private ListView mListView;
    private TextView mAddNewPlan;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<PlanListModel.RowsBean> mData;
    private List<PlanListModel.RowsBean> mTestData;
    private PlanListAdapter mAdatper;
    private MainPresenter mPresenter;
    private CircleImageView mAvatar;
    private TextView mName;
    private TextView mBalance;
    private UserInfo.UserInfoBean mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        getWindow().setReenterTransition(explode);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        registerListener();
        refreshPlanList();
    }

    private void refreshPlanList() {
        mData.clear();
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getPlanList();
    }

    private void initData() {
        mPresenter = new MainPresenter(this);
        mUserInfo = getUserInfo();
        mTestData = new ArrayList<PlanListModel.RowsBean>();
    }

    private void initView() {
        mAddNewPlan = (TextView) findViewById(R.id.fab);
        mListView = (ListView) findViewById(R.id.listView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mAvatar = (CircleImageView) findViewById(R.id.avatar);
        mName = (TextView) findViewById(R.id.name);
        mBalance = (TextView) findViewById(R.id.balance);
        initUserInfoPanel();
    }

    /**
     * get user info from loginActivity
     *
     * @return UserInfoBean:userName,userId,userBalance,userAvatar
     */
    private UserInfo.UserInfoBean getUserInfo() {
        AppController.getInstance().setUserInfo(getIntent().getStringExtra(Constant.USER_NAME)
                , getIntent().getIntExtra(Constant.USER_ID, -1), getIntent().getIntExtra(Constant.USER_BALANCE, -1)
                , Constant.AVATAR_PREFIX + getIntent().getStringExtra(Constant.USER_AVATAR));
        return AppController.getInstance().getUserInfo();
    }

    /**
     * init User Info UI at top of the screen
     */
    private void initUserInfoPanel() {
        mName.setText(mUserInfo.getUserName());
        mBalance.setText(String.valueOf(mUserInfo.getBalance()));
        Glide.with(this).load(mUserInfo.getAvatar()).centerCrop().crossFade().into(mAvatar);
    }

    private void registerListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAddNewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 新建计划
                Intent intent = new Intent(MainActivity.this, AddPlanActivity.class);
                intent.putExtra(Constant.USER_ID, mUserInfo.getId());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        if (mData != null && mData.size() != 0) {
            mData.clear();
        }
        mPresenter.getPlanList();
    }

    @Override
    public void onRequestSuccess(List<PlanListModel.RowsBean> data) {
        mData = data;
        mSwipeRefreshLayout.setRefreshing(false);
        updateListView();
    }

    @Override
    public void onUpdateUserBalance(String balance) {
        mBalance.setText(balance);
    }

    @Override
    public Context onGetContext() {
        return getApplicationContext();
    }

    @Override
    public String onGetUserId() {
        return String.valueOf(mUserInfo.getId());
    }

    private void updateListView() {
        if (mAdatper == null) {
            mAdatper = new PlanListAdapter(this, mData);
            mAdatper.setCardViewListener(this);
            mListView.setAdapter(mAdatper);
        } else {
            mAdatper.notifyDataSetChanged();
        }
    }

    @Override
    public void onCardViewClick(int position) {
        PlanListModel.RowsBean rowsBean = mData.get(position);
        Intent intent = new Intent(MainActivity.this, PlanDetailActivity.class);
        intent.putExtra(Constant.PLAN_ID, rowsBean.getId());
        intent.putExtra(Constant.TITLE, rowsBean.getUserName() + "的健身计划");
        intent.putExtra(Constant.USER_AVATAR, Constant.AVATAR_PREFIX + rowsBean.getUserAvatar());
        intent.putExtra(Constant.AVATAR_SELF, mUserInfo.getAvatar());
        intent.putExtra(Constant.USER_ID, mUserInfo.getId());
        if (mUserInfo.getId() == rowsBean.getUserId()) {
            intent.putExtra(Constant.DISMISS_ADD_GROUP, true);
        }
        List<PlanListModel.RowsBean.SupervisorsBean> supervison = rowsBean.getSupervisors();
        try {
            if (supervison != null && supervison.size() != 0) {
                if (supervison.get(0) != null) {
                    intent.putExtra(Constant.FOLLOWER_1, Constant.AVATAR_PREFIX + supervison.get(0).getAvatar());
                }
                if (supervison.get(1) != null) {
                    intent.putExtra(Constant.FOLLOWER_2, Constant.AVATAR_PREFIX + supervison.get(1).getAvatar());
                }
                if (supervison.get(2) != null) {
                    intent.putExtra(Constant.FOLLOWER_3, Constant.AVATAR_PREFIX + supervison.get(2).getAvatar());
                }
                if (supervison.get(3) != null) {
                    intent.putExtra(Constant.FOLLOWER_4, Constant.AVATAR_PREFIX + supervison.get(3).getAvatar());
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            startActivity(intent);
        }
    }
}
