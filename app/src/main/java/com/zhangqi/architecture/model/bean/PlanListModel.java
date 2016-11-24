package com.zhangqi.architecture.model.bean;

import java.util.List;

/**
 * Created by zhangqi on 16/11/14.
 */
public class PlanListModel {

    /**
     * code : 0
     * rows : [{"planId":"1","name":"zhangqi","cash":"500","avatar":"http://www.sdfs.jpg","supervison":[{"name":"jiguangteng","avatar":"http://www.sfs.jpg"},{"name":"shangjing","avatar":"http://www.sdfa.jpg"}]},{"planId":"2","name":"jiguangteng","avatar":"http://www.sfs.jpg","supervison":[{"name":"zhangqi","avatar":"http://www.sfs.jpg"},{"name":"shangjing","avatar":"http://www.sdfa.jpg"}]}]
     */

    private String code;
    /**
     * planId : 1
     * name : zhangqi
     * cash : 500
     * avatar : http://www.sdfs.jpg
     * supervison : [{"name":"jiguangteng","avatar":"http://www.sfs.jpg"},{"name":"shangjing","avatar":"http://www.sdfa.jpg"}]
     */

    private List<RowsBean> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private String planId;
        private String name;
        private String cash;
        private String avatar;
        /**
         * name : jiguangteng
         * avatar : http://www.sfs.jpg
         */

        private List<SupervisonBean> supervison;

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public List<SupervisonBean> getSupervison() {
            return supervison;
        }

        public void setSupervison(List<SupervisonBean> supervison) {
            this.supervison = supervison;
        }

        public static class SupervisonBean {
            private String name;
            private String avatar;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}
