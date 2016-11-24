package com.zhangqi.architecture.model.bean;

import java.util.List;

/**
 * Created by zhangqi on 16/11/20.
 */
public class PlanDetailModel {

    /**
     * code : 0
     * rows : {"planId":"1","name":"zhangqi","cash":"500","avatar":"http://www.sdfs.jpg","supervison":[{"name":"jiguangteng","avatar":"http://www.sfs.jpg"},{"name":"shangjing","avatar":"http://www.sdfa.jpg"}],"detail":[{"detailId":"1","date":"2016-11-14","plan":"跑步5公里，哑铃100个","result":[{"avatar":"http://www.fsfs.jpg","judge":"true"},{"avatar":"http://www.fss.jpg","judge":"false"}]},{"detailId":"2","date":"2016-11-15","plan":"跳绳1000个，俯卧撑20个","result":[{"avatar":"http://www.fsfs.jpg","judge":"true"},{"avatar":"http://www.fss.jpg","judge":"false"}]}]}
     */

    private String code;
    /**
     * planId : 1
     * name : zhangqi
     * cash : 500
     * avatar : http://www.sdfs.jpg
     * supervison : [{"name":"jiguangteng","avatar":"http://www.sfs.jpg"},{"name":"shangjing","avatar":"http://www.sdfa.jpg"}]
     * detail : [{"detailId":"1","date":"2016-11-14","plan":"跑步5公里，哑铃100个","result":[{"avatar":"http://www.fsfs.jpg","judge":"true"},{"avatar":"http://www.fss.jpg","judge":"false"}]},{"detailId":"2","date":"2016-11-15","plan":"跳绳1000个，俯卧撑20个","result":[{"avatar":"http://www.fsfs.jpg","judge":"true"},{"avatar":"http://www.fss.jpg","judge":"false"}]}]
     */

    private RowsBean rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
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
        /**
         * detailId : 1
         * date : 2016-11-14
         * plan : 跑步5公里，哑铃100个
         * result : [{"avatar":"http://www.fsfs.jpg","judge":"true"},{"avatar":"http://www.fss.jpg","judge":"false"}]
         */

        private List<DetailBean> detail;

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

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
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

        public static class DetailBean {
            private String detailId;
            private String date;
            private String plan;
            private int state;

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            /**
             * avatar : http://www.fsfs.jpg
             * judge : true
             */



            private List<ResultBean> result;

            public String getDetailId() {
                return detailId;
            }

            public void setDetailId(String detailId) {
                this.detailId = detailId;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getPlan() {
                return plan;
            }

            public void setPlan(String plan) {
                this.plan = plan;
            }

            public List<ResultBean> getResult() {
                return result;
            }

            public void setResult(List<ResultBean> result) {
                this.result = result;
            }

            public static class ResultBean {
                private String avatar;
                private int judge;

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
            }
        }
    }
}
