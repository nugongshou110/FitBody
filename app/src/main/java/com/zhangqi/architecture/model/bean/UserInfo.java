package com.zhangqi.architecture.model.bean;

/**
 * Created by zhangqi on 16/11/25.
 */
public class UserInfo {

    /**
     * code : 0
     * obj : {"avatar":"/media/ji/document/school/arc/photo/1.jpeg","balance":0,"id":1,"password":"123","userName":"ji"}
     */

    private int code;
    /**
     * avatar : /media/ji/document/school/arc/photo/1.jpeg
     * balance : 0
     * id : 1
     * password : 123
     * userName : ji
     */

    private UserInfoBean obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserInfoBean getObj() {
        return obj;
    }

    public void setObj(UserInfoBean obj) {
        this.obj = obj;
    }

    public static class UserInfoBean {
        private String avatar;
        private int balance;
        private int id;
        private String password;
        private String userName;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
