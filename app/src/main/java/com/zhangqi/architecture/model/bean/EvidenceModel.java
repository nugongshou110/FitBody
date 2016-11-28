package com.zhangqi.architecture.model.bean;

import java.util.List;

/**
 * Created by zhangqi on 16/11/28.
 */
public class EvidenceModel {

    /**
     * code : 0
     * rows : [{"avatar":"/media/ji/document/school/arc/evidence/1_1480077946965/idea.jpg","comment":"测试评论","created":"2016-11-25 20:45:46","id":1,"planItemId":1}]
     */

    private int code;
    /**
     * avatar : /media/ji/document/school/arc/evidence/1_1480077946965/idea.jpg
     * comment : 测试评论
     * created : 2016-11-25 20:45:46
     * id : 1
     * planItemId : 1
     */

    private List<RowsBean> rows;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
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
