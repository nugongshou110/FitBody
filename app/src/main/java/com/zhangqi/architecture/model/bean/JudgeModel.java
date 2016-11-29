package com.zhangqi.architecture.model.bean;

/**
 * Created by zhangqi on 16/11/28.
 */
public class JudgeModel {
    private int userId;
    private int planItemId;
    private String comment;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlanItemId() {
        return planItemId;
    }

    public void setPlanItemId(int planItemId) {
        this.planItemId = planItemId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
