package com.zhangqi.architecture.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangqi.architecture.R;
import com.zhangqi.architecture.adapter.api.ICardViewListener;
import com.zhangqi.architecture.model.bean.PlanDetailModel;
import com.zhangqi.architecture.view.widget.JudgeView;

import java.util.List;

/**
 * Created by zhangqi on 16/11/20.
 */
public class PlanDetailAdapter extends BaseAdapter {
    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<PlanDetailModel.RowsBean> mDatas;
    private String mPlanName;
    private ICardViewListener mListener;

    public PlanDetailAdapter(Activity activity, List<PlanDetailModel.RowsBean> data, String planName) {
        mInflater = LayoutInflater.from(activity);
        mActivity = activity;
        mDatas = data;
        mPlanName = planName;
    }

    public void setIPlanDetailListener(ICardViewListener listener) {
        mListener = listener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.plan_detail_item, parent, false);
            viewHolder.plan_detail_container = (RelativeLayout) convertView.findViewById(R.id.plan_detail_container);
            viewHolder.plan_detail_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onCardViewClick(position);
                }
            });
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
        PlanDetailModel.RowsBean bean = mDatas.get(position);
        viewHolder.mDate.setText(bean.getPlanDate());
        viewHolder.mPlan.setText(mPlanName);
        int state = bean.getStatus();
        switch (state) {
            case 0:
                viewHolder.mState.setText("未开始");
                viewHolder.mState.setBackground(mActivity.getDrawable(R.drawable.bg_round_state_not_start));
                break;
            case 1:
                viewHolder.mState.setText("进行中");
                viewHolder.mState.setBackground(mActivity.getDrawable(R.drawable.bg_round_state_ongoing));
                break;
            case 2:
                viewHolder.mState.setText("成功");
                viewHolder.mState.setBackground(mActivity.getDrawable(R.drawable.bg_round_state_success));
                break;
            case 3:
                viewHolder.mState.setText("失败");
                viewHolder.mState.setBackground(mActivity.getDrawable(R.drawable.bg_round_state_fail));
                break;
        }

        return convertView;
    }

    private static class ViewHolder {
        private RelativeLayout plan_detail_container;
        private TextView mDate;
        private TextView mPlan;
        private Button mState;
        private JudgeView mJudge_1, mJudge_2, mJudge_3, mJudge_4;
    }
}
