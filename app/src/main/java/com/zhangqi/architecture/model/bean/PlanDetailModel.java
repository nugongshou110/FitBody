package com.zhangqi.architecture.model.bean;

import java.util.List;

/**
 * Created by zhangqi on 16/11/20.
 */
public class PlanDetailModel {

    /**
     * code : 0
     * obj : {"endTime":"2016-11-30 00:00:00","id":1,"money":200,"planName":"testPlan","startTime":"2016-11-10 00:00:00","status":1,"supervisors":[{"avatar":"/media/ji/document/school/arc/photo/1.jpeg","id":1,"userName":"ji"}],"userAvatar":"/media/ji/document/school/arc/photo/1.jpeg","userId":1,"userName":"ji"}
     * rows : [{"id":1,"planDate":"2016-11-25 00:00:00","planId":1,"status":1},{"id":2,"planDate":"2016-11-26 00:00:00","planId":1,"status":1},{"id":3,"planDate":"2016-11-27 00:00:00","planId":1,"status":1},{"id":4,"planDate":"2016-11-28 00:00:00","planId":1,"status":1},{"id":5,"planDate":"2016-11-29 00:00:00","planId":1,"status":1},{"id":6,"planDate":"2016-11-30 00:00:00","planId":1,"status":1}]
     */

    private int code;
    /**
     * endTime : 2016-11-30 00:00:00
     * id : 1
     * money : 200
     * planName : testPlan
     * startTime : 2016-11-10 00:00:00
     * status : 1
     * supervisors : [{"avatar":"/media/ji/document/school/arc/photo/1.jpeg","id":1,"userName":"ji"}]
     * userAvatar : /media/ji/document/school/arc/photo/1.jpeg
     * userId : 1
     * userName : ji
     */

    private ObjBean obj;
    /**
     * id : 1
     * planDate : 2016-11-25 00:00:00
     * planId : 1
     * status : 1
     */

    private List<RowsBean> rows;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class ObjBean {
        private String endTime;
        private int id;
        private int money;
        private String planName;
        private String startTime;
        private int status;
        private String userAvatar;
        private int userId;
        private String userName;
        /**
         * avatar : /media/ji/document/school/arc/photo/1.jpeg
         * id : 1
         * userName : ji
         */

        private List<SupervisorsBean> supervisors;

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public List<SupervisorsBean> getSupervisors() {
            return supervisors;
        }

        public void setSupervisors(List<SupervisorsBean> supervisors) {
            this.supervisors = supervisors;
        }

        public static class SupervisorsBean {
            private String avatar;
            private int id;
            private String userName;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }

    public static class RowsBean {
        private int id;
        private String planDate;
        private int planId;
        private int status;
        private String evidencePhoto;

        public String getEvidencePhoto() {
            return evidencePhoto;
        }

        public void setEvidencePhoto(String evidencePhoto) {
            this.evidencePhoto = evidencePhoto;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlanDate() {
            return planDate;
        }

        public void setPlanDate(String planDate) {
            this.planDate = planDate;
        }

        public int getPlanId() {
            return planId;
        }

        public void setPlanId(int planId) {
            this.planId = planId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
