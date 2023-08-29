package com.example.light.common;

import com.example.light.dto.curUserDto;
import com.example.light.entity.Permission;
import com.example.light.entity.User;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.List;

public class UserUtil {

//	public static User getCurrentUser() {
////		User user = (User) getSession().getAttribute(UserConstants.LOGIN_USER);
//		User user = (User) getSession().getAttribute(UserConstants.LOGIN_USER);
//
//		return user;
//	}

//	public static void setUserSession(User user) {
//		//user包含userId,userName,userArea,还需要添加userRoleName，userAreaName
//		getSession().setAttribute(UserConstants.LOGIN_USER, user);
//	}

	public static curUserDto getCurrentUser() {
//		User user = (User) getSession().getAttribute(UserConstants.LOGIN_USER);
		curUserDto user = (curUserDto) getSession().getAttribute(UserConstants.LOGIN_USER);

		return user;
	}
	public static void setUserSession(curUserDto user) {
		//user包含userId,userName,userArea,还需要添加userRoleName，userAreaName
		getSession().setAttribute(UserConstants.LOGIN_USER, user);
	}

	@SuppressWarnings("unchecked")
	public static List<Permission> getCurrentPermissions() {
		List<Permission> list = (List<Permission>) getSession().getAttribute(UserConstants.USER_PERMISSIONS);

		return list;
	}

	public static void setPermissionSession(List<Permission> list) {
		getSession().setAttribute(UserConstants.USER_PERMISSIONS, list);
	}

	public static Session getSession() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();

		return session;
	}
}
