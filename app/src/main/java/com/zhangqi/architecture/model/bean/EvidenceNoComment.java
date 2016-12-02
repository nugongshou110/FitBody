package com.zhangqi.architecture.model.bean;

/**
 * Created by zhangqi on 16/12/1.
 */
public class EvidenceNoComment {

    /**
     * code : 0
     * obj : {"avatar":"/data/arc/evidence/67_1480599386103/wuyanzu.png","comment":"","created":"2016-12-01 21:36:27","id":3,"planItemId":67}
     */

    private int code;
    /**
     * avatar : /data/arc/evidence/67_1480599386103/wuyanzu.png
     * comment :
     * created : 2016-12-01 21:36:27
     * id : 3
     * planItemId : 67
     */

    private ObjBean obj;

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
}
