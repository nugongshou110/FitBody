package com.zhangqi.architecture.model.bean;

/**
 * Created by zhangqi on 16/11/27.
 */
public class AddPlanModel {

    /**
     * planName : testPlan
     * startTime : 2016-11-10
     * endTime : 2016-11-30
     * money : 200
     * userId : 1
     */

    private String planName;
    private String startTime;
    private String endTime;
    private String money;
    private int userId;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
