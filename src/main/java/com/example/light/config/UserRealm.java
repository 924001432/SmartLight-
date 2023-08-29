package com.example.light.config;

import com.example.light.common.UserUtil;
import com.example.light.dto.curUserDto;
import com.example.light.entity.Permission;
import com.example.light.entity.Role;
import com.example.light.entity.RolePermission;
import com.example.light.entity.User;
import com.example.light.service.PermissionService;
import com.example.light.service.RolePermissionService;
import com.example.light.service.RoleService;
import com.example.light.service.UserService;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限认证
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
//        User queryUser = new User();
//        queryUser.setUserName(username);

        //根据用户名查询用户是否存在
        User user = userService.queryUserByName(username);



        //用户不存在
        if(user==null){
            return null;
        }
        //密码错误
        //状态无效



        curUserDto curUser = new curUserDto(user);

        UserUtil.setUserSession(curUser);
        System.out.println(curUser.toString());


        return new SimpleAuthenticationInfo(user,user.getUserPassword(),getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //查询用户信息
        User user = UserUtil.getCurrentUser();
//        System.out.println(user.toString());
        //通过用户角色ID查询用户角色信息
        Role role = roleService.queryRoleById(user.getUserRole());
//        System.out.println(role.toString());
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        Set<String> roleName = roles.stream().map(Role::getRoleName).collect(Collectors.toSet());;

        authorizationInfo.setRoles(roleName);
        //根据用户ID,查询角色ID，查询用户权限列表，获取权限列表
        List<Permission> permissionList = new ArrayList<>();
        List<RolePermission> rolePermissions = rolePermissionService.queryRolePermissionById(role.getRoleId());
        //{1,2,3,4,5}
        for (RolePermission rolePermission : rolePermissions) {
//            System.out.println(rolePermission);
            permissionList.add(permissionService.queryPermissionBypermissionId(rolePermission.getPermissionId()));
        }
//        for (Permission permission : permissionList) {
//            System.out.println(permission);
//        }

        //设置会话属性
        UserUtil.setPermissionSession(permissionList);
        Set<String> permissions = permissionList.stream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
                .map(Permission::getPermission).collect(Collectors.toSet());
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    /**
     * 重写缓存key，否则集群下session共享时，会重复执行doGetAuthorizationInfo权限配置
     */
    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) principals;
        Object object = principalCollection.getPrimaryPrincipal();

        if (object instanceof User) {
            User user = (User) object;

            return "authorization:cache:key:users:" + user.getUserId();
        }

        return super.getAuthorizationCacheKey(principals);
    }
}











