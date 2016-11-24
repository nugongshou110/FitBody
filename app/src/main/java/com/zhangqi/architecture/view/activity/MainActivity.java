package com.zhangqi.architecture.view.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.zhangqi.architecture.R;
import com.zhangqi.architecture.adapter.PlanListAdapter;
import com.zhangqi.architecture.adapter.api.ICardViewListener;
import com.zhangqi.architecture.model.bean.PlanListModel;
import com.zhangqi.architecture.presenter.MainPresenter;
import com.zhangqi.architecture.presenter.api.IMainViewListener;
import com.zhangqi.architecture.util.Constant;
import com.zhangqi.architecture.view.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, IMainViewListener,ICardViewListener{
    private ListView mListView;
    private FloatingActionButton mAddNewPlan;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<PlanListModel.RowsBean> mData;
    private List<PlanListModel.RowsBean> mTestData;
    private PlanListAdapter mAdatper;
    private MainPresenter mPresenter;
    private CircleImageView mAvatar;
    private TextView mName;
    private TextView mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        getWindow().setReenterTransition(explode);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        mTestData = new ArrayList<PlanListModel.RowsBean>();
        initView();
        registerListener();
        //TODO test data
        onRequestSuccess(mTestData);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                register();
            }
        }).start();

    }

//    private void register() {
//        String avatar = "sdcard/Pictures/wuyanzu.png";
//        String url = "http://183.173.37.144:8080/arc/user/register";
//        UploadPhoto.getInstance().doUpload(url,"\"userName\":\"jiguangteng\",\"password\":\"asdffdsa\"",avatar);
//    }

    private void login(){

    }

    private void initView() {
        mAddNewPlan = (FloatingActionButton) findViewById(R.id.fab);
        mListView = (ListView) findViewById(R.id.listView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mAvatar = (CircleImageView) findViewById(R.id.avatar);
        mName = (TextView) findViewById(R.id.name);
        mScore = (TextView) findViewById(R.id.score);
    }

    private void registerListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAddNewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 新建计划
                Intent intent = new Intent(MainActivity.this, AddPlanActivity.class);
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
        PlanListModel.RowsBean plan1 = new PlanListModel.RowsBean();
        plan1.setPlanId("1");
        plan1.setName("张奇");
        plan1.setCash("500元");
        plan1.setAvatar("http://h.hiphotos.baidu.com/zhidao/pic/item/0eb30f2442a7d9334f268ca9a84bd11372f00159.jpg");
        List<PlanListModel.RowsBean.SupervisonBean> followers = new ArrayList<>();
        PlanListModel.RowsBean.SupervisonBean follower1 = new PlanListModel.RowsBean.SupervisonBean();
        follower1.setName("姬广滕");
        follower1.setAvatar("http://img3.duitang.com/uploads/item/201507/30/20150730163111_YZT5S.thumb.700_0.jpeg");
        PlanListModel.RowsBean.SupervisonBean follower2 = new PlanListModel.RowsBean.SupervisonBean();
        follower2.setName("尚静");
        follower2.setAvatar("http://a.hiphotos.baidu.com/zhidao/pic/item/f9dcd100baa1cd11aa2ca018bf12c8fcc3ce2d74.jpg");
        followers.add(follower1);
        followers.add(follower2);
        plan1.setSupervison(followers);
        mTestData.add(0,plan1);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRequestSuccess(List<PlanListModel.RowsBean> data) {
        mData = data;
        PlanListModel.RowsBean plan1 = new PlanListModel.RowsBean();
        plan1.setPlanId("1");
        plan1.setName("张奇");
        plan1.setCash("500元");
        plan1.setAvatar("http://h.hiphotos.baidu.com/zhidao/pic/item/0eb30f2442a7d9334f268ca9a84bd11372f00159.jpg");
        List<PlanListModel.RowsBean.SupervisonBean> followers = new ArrayList<>();
        PlanListModel.RowsBean.SupervisonBean follower1 = new PlanListModel.RowsBean.SupervisonBean();
        follower1.setName("姬广滕");
        follower1.setAvatar("http://183.173.37.144:8080/arc/user/getImage?url=/media/ji/document/school/arc/photo/1.jpeg");
        PlanListModel.RowsBean.SupervisonBean follower2 = new PlanListModel.RowsBean.SupervisonBean();
        follower2.setName("尚静");
        follower2.setAvatar("http://a.hiphotos.baidu.com/zhidao/pic/item/f9dcd100baa1cd11aa2ca018bf12c8fcc3ce2d74.jpg");
        followers.add(follower1);
        followers.add(follower2);
        plan1.setSupervison(followers);
        mTestData.add(plan1);
        mTestData.add(plan1);
        mTestData.add(plan1);
        mTestData.add(plan1);
        mTestData.add(plan1);
        mTestData.add(plan1);
        updateListView();
    }

    private void updateListView() {
        if (mAdatper == null) {
            mAdatper = new PlanListAdapter(this, mTestData);
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
        intent.putExtra(Constant.TITLE, rowsBean.getName() + "的健身计划");
        intent.putExtra(Constant.AVATAR, rowsBean.getAvatar());
        List<PlanListModel.RowsBean.SupervisonBean> supervison = rowsBean.getSupervison();
        try{
            if (supervison != null && supervison.size() != 0) {
                if (supervison.get(0) != null) {
                    intent.putExtra(Constant.FOLLOWER_1, supervison.get(0).getAvatar());
                }
                if (supervison.get(1) != null) {
                    intent.putExtra(Constant.FOLLOWER_2, supervison.get(1).getAvatar());
                }
                if (supervison.get(2) != null) {
                    intent.putExtra(Constant.FOLLOWER_3, supervison.get(2).getAvatar());
                }
                if (supervison.get(3) != null) {
                    intent.putExtra(Constant.FOLLOWER_4, supervison.get(3).getAvatar());
                }
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }finally {
            startActivity(intent);
        }
    }
}
