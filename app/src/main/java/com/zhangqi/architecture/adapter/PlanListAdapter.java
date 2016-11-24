package com.zhangqi.architecture.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhangqi.architecture.R;
import com.zhangqi.architecture.adapter.api.ICardViewListener;
import com.zhangqi.architecture.model.bean.PlanListModel;
import com.zhangqi.architecture.view.widget.CircleImageView;

import java.util.List;

/**
 * Created by zhangqi on 16/11/14.
 */
public class PlanListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Activity mActivity;
    private List<PlanListModel.RowsBean> mData;
    private ICardViewListener mCardViewListener;

    public PlanListAdapter(Activity activity, List<PlanListModel.RowsBean> data) {
        mInflater = LayoutInflater.from(activity);
        mActivity = activity;
        mData = data;
    }

    public void setCardViewListener(ICardViewListener listener){
        mCardViewListener = listener;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mAvatar = (CircleImageView) convertView.findViewById(R.id.item_avatar);
            viewHolder.mFollower_1 = (CircleImageView) convertView.findViewById(R.id.follower_1);
            viewHolder.mFollower_2 = (CircleImageView) convertView.findViewById(R.id.follower_2);
            viewHolder.mFollower_3 = (CircleImageView) convertView.findViewById(R.id.follower_3);
            viewHolder.mFollower_4 = (CircleImageView) convertView.findViewById(R.id.follower_4);
            viewHolder.mCardView = (CardView) convertView.findViewById(R.id.cardview);
            viewHolder.mName = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.mCash = (Button) convertView.findViewById(R.id.item_cash);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PlanListModel.RowsBean rowsBean = mData.get(position);
        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCardViewListener != null){
                    mCardViewListener.onCardViewClick(position);
                }
            }
        });
        viewHolder.mName.setText(rowsBean.getName());
        viewHolder.mCash.setText(rowsBean.getCash());
        Glide.with(mActivity)
                .load(rowsBean.getAvatar())
                .centerCrop()
                .crossFade()
                .into(viewHolder.mAvatar);
        List<PlanListModel.RowsBean.SupervisonBean> supervison = rowsBean.getSupervison();
        if (supervison != null && supervison.size() != 0) {
            try {
                if (supervison.get(0) != null) {
                    Glide.with(mActivity)
                            .load(supervison.get(0).getAvatar())
                            .centerCrop().crossFade().into(viewHolder.mFollower_1);
                }
                if (supervison.get(1) != null) {
                    Glide.with(mActivity)
                            .load(supervison.get(1).getAvatar())
                            .centerCrop().crossFade().into(viewHolder.mFollower_2);
                }
                if (supervison.get(2) != null) {
                    Glide.with(mActivity)
                            .load(supervison.get(2).getAvatar())
                            .centerCrop().crossFade().into(viewHolder.mFollower_3);
                }
                if (supervison.get(3) != null) {
                    Glide.with(mActivity)
                            .load(supervison.get(3).getAvatar())
                            .crossFade().crossFade().into(viewHolder.mFollower_4);
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        }

        return convertView;
    }

    private static class ViewHolder {
        private CardView mCardView;
        private CircleImageView mAvatar, mFollower_1, mFollower_2, mFollower_3, mFollower_4;
        private TextView mName;
        private Button mCash;
    }
}
