package com.zhangqi.architecture.model.bean;

import java.util.List;

/**
 * Created by zhangqi on 16/11/28.
 */
public class EvidenceModel {


    /**
     * code : 0
     * obj : {"avatar":"/data/arc/evidence/1_1480333592592/idea.jpg","comment":"测试评论","created":"2016-11-28 19:46:34","id":2,"planItemId":1}
     * rows : [{"avatar":"/media/ji/document/school/arc/photo/1.jpeg","judge":1,"userId":1,"userName":"ji"}]
     */

    private int code;
    /**
     * avatar : /data/arc/evidence/1_1480333592592/idea.jpg
     * comment : 测试评论
     * created : 2016-11-28 19:46:34
     * id : 2
     * planItemId : 1
     */

    private ObjBean obj;
    /**
     * avatar : /media/ji/document/school/arc/photo/1.jpeg
     * judge : 1
     * userId : 1
     * userName : ji
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
        private String avatar;
        private String comment;
        private String created;
        private int id;
        private int planItemId;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPlanItemId() {
            return planItemId;
        }

        public void setPlanItemId(int planItemId) {
            this.planItemId = planItemId;
        }
    }

    public static class RowsBean {
        private String avatar;
        private int judge;
        private int userId;
        private String userName;
        private String comment;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getJudge() {
            return judge;
        }

        public void setJudge(int judge) {
            this.judge = judge;
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
    }
}
