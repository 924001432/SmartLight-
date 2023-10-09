package com.example.light.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
	String module() default "";
}
/*@RequestMapping(value = "/wxlogin", method = {RequestMethod.POST,RequestMethod.GET})
@ResponseBodypublic Map<String,String>  wxlogin(Userlogin userlogin, Model model) throws Exception{
//Shiro实现登录
Map<String,String> map = new HashMap<String, String>();
UsernamePasswordToken token = new UsernamePasswordToken(userlogin.getUsername(),userlogin.getPassword());//Subject：项目，通过Shiro保护的项目一个抽象概念//通过令牌（token）与项目（subject）的登陆（login）关系，Shiro保证了项目整体的安全//获取Subject单例对象Subject subject = SecurityUtils.getSubject();//如果获取不到用户名就是登录失败，但登录失败的话，会直接抛出异常//登录subject.login(token);if (subject.hasRole("admin")) {map.put("role","admin");map.put("username",userlogin.getUsername());} else if (subject.hasRole("teacher")) {map.put("role","teacher");map.put("username",userlogin.getUsername());} else if (subject.hasRole("student")) {map.put("role","student");map.put("username",userlogin.getUsername());}return map;}

*
*/