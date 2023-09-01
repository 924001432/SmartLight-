package com.example.light.vo;

import com.example.light.entity.User;

public class UserVo extends User {

    public UserVo(User user) {
        this.setUserId(user.getUserId());
        this.setUserName(user.getUserName());
        this.setUserRole(user.getUserRole());
        this.setUserArea(user.getUserArea());

    }

    private String userRoleName;

    private String userAreaName;

    private String userBuilderName;


    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getUserAreaName() {
        return userAreaName;
    }

    public void setUserAreaName(String userAreaName) {
        this.userAreaName = userAreaName;
    }

    public String getUserBuilderName() {
        return userBuilderName;
    }

    public void setUserBuilderName(String userBuilderName) {
        this.userBuilderName = userBuilderName;
    }

}
