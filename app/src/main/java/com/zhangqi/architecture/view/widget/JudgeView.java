package com.zhangqi.architecture.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by zhangqi on 16/11/20.
 */
public class JudgeView extends LinearLayout {
    private String mAvatar;
    private int mJudge;
    public JudgeView(Context context) {
        super(context);
        init(context);
    }

    public JudgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JudgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context){
        setOrientation(HORIZONTAL);



        ImageView judge = new ImageView(context);
        ViewGroup.LayoutParams judgeParams = new ViewGroup.LayoutParams(40,40);
        judge.setLayoutParams(judgeParams);
        judge.setPadding(16, 16, 16, 16);

        CircleImageView avatar = new CircleImageView(context);
        ViewGroup.LayoutParams avatarParams = new ViewGroup.LayoutParams(40,40);
        avatar.setLayoutParams(avatarParams);
        avatar.setPadding(16,16,16,16);

        addView(avatar);
        addView(judge);
    }

    public void setAvatar(String avatar){
        mAvatar = avatar;
    }
    public void setJudge(int judge){
        mJudge = judge;
    }
}
