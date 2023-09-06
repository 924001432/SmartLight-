package com.example.light.advice;

import com.example.light.annotation.LogAnnotation;
import com.example.light.common.UserUtil;
import com.example.light.dto.curUserDto;
import com.example.light.entity.SysLogs;
import com.example.light.service.SyslogsService;

import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 统一日志处理
 * 
 * @author 小威老师 xiaoweijiagou@163.com
 *
 *         2017年8月19日
 */
@Aspect
@Component
public class LogAdvice {

	@Autowired
	private SyslogsService logService;

	@Around(value = "@annotation(com.example.light.annotation.LogAnnotation)")
	public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
		SysLogs sysLogs = new SysLogs();
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

		String module = null;
		LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
		module = logAnnotation.module();
		if (StringUtils.isEmpty(module)) {
			ApiOperation apiOperation = methodSignature.getMethod().getDeclaredAnnotation(ApiOperation.class);
			if (apiOperation != null) {
				module = apiOperation.value();
			}
		}

		if (StringUtils.isEmpty(module)) {
			throw new RuntimeException("没有指定日志module");
		}
		sysLogs.setLogsModule(module);

		try {
			Object object = joinPoint.proceed();

			curUserDto curUser = UserUtil.getCurrentUser();

			sysLogs.setUserName(curUser.getUserName());

			sysLogs.setLogsFlag(1);



			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_time = sf.format(date);

			sysLogs.setLogsCreatetime(create_time);

			logService.save(sysLogs);


			return object;
		} catch (Exception e) {
			curUserDto curUser = UserUtil.getCurrentUser();

			sysLogs.setUserName(curUser.getUserName());

			sysLogs.setLogsFlag(0);
			sysLogs.setLogsRemark(e.getMessage());

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_time = sf.format(date);

			sysLogs.setLogsCreatetime(create_time);

			logService.save(sysLogs);
			throw e;
		}

	}
}
