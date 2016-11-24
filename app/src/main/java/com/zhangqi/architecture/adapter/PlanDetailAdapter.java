package com.zhangqi.architecture.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zhangqi.architecture.R;
import com.zhangqi.architecture.model.bean.PlanDetailModel;
import com.zhangqi.architecture.view.widget.JudgeView;

import java.util.ArrayList;

/**
 * Created by zhangqi on 16/11/20.
 */
public class PlanDetailAdapter extends BaseAdapter {
    private Activity mActivity;
    private LayoutInflater mInflater;
    private ArrayList<PlanDetailModel.RowsBean.DetailBean> mDatas;

    public PlanDetailAdapter(Activity activity, ArrayList<PlanDetailModel.RowsBean.DetailBean> data) {
        mInflater = LayoutInflater.from(activity);
        mActivity = activity;
        mDatas = data;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.plan_detail_item, parent, false);
            viewHolder.mDate = (TextView) convertView.findViewById(R.id.date);
            viewHolder.mPlan = (TextView) convertView.findViewById(R.id.plan);
            viewHolder.mState = (Button) convertView.findViewById(R.id.state);
            viewHolder.mJudge_1 = (JudgeView) convertView.findViewById(R.id.judger_1);
            viewHolder.mJudge_2 = (JudgeView) convertView.findViewById(R.id.judger_2);
            viewHolder.mJudge_3 = (JudgeView) convertView.findViewById(R.id.judger_3);
            viewHolder.mJudge_4 = (JudgeView) convertView.findViewById(R.id.judger_4);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PlanDetailModel.RowsBean.DetailBean detailBean = mDatas.get(position);
        viewHolder.mDate.setText(detailBean.getDate());
        viewHolder.mPlan.setText(detailBean.getPlan());
        int state = detailBean.getState();
        switch (state) {
            case 0:
                viewHolder.mState.setText("未开始");
                viewHolder.mState.setBackground(mActivity.getDrawable(R.drawable.bg_round_state_not_start));
                break;
            case 1:
                viewHolder.mState.setText("进行中");
                viewHolder.mState.setBackground(mActivity.getDrawable(R.drawable.bg_round_state_ongoing));
                break;
        }

        return convertView;
    }

    private static class ViewHolder {
        private TextView mDate;
        private TextView mPlan;
        private Button mState;
        private JudgeView mJudge_1, mJudge_2, mJudge_3, mJudge_4;
    }
}
