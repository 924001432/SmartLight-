package com.example.light.dto;

import com.example.light.entity.User;

public class curUserDto extends User {

    public curUserDto(User user) {
        this.setUserId(user.getUserId());
        this.setUserName(user.getUserName());
        this.setUserRole(user.getUserRole());
        this.setUserArea(user.getUserArea());

    }

    private String userRoleName;

    private String userAreaName;

    private Integer userRoleLevel;


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

    public Integer getUserRoleLevel() {
        return userRoleLevel;
    }

    public void setUserRoleLevel(Integer userRoleLevel) {
        this.userRoleLevel = userRoleLevel;
    }
}
