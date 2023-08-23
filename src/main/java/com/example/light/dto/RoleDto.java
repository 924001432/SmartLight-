package com.example.light.dto;

import com.example.light.entity.Role;

import java.util.List;

public class RoleDto extends Role {

    private List<Integer> permissionIds;

    public List<Integer> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Integer> permissionIds) {
        this.permissionIds = permissionIds;
    }

}
