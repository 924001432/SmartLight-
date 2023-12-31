package com.example.light.advice;

import com.example.light.dto.ResponseInfo;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * springmvc异常处理
 * 
 * @author 小威老师 xiaoweijiagou@163.com
 *
 */

@RestControllerAdvice
public class ExceptionHandlerAdvice {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseInfo badRequestException(IllegalArgumentException exception) {
		return new ResponseInfo(HttpStatus.BAD_REQUEST.value() + "", exception.getMessage());
	}

	@ExceptionHandler({ UnknownAccountException.class, IncorrectCredentialsException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseInfo loginException(Exception exception) {
		return new ResponseInfo(HttpStatus.UNAUTHORIZED.value() + "", exception.getMessage());
	}

	@ExceptionHandler({ UnauthorizedException.class })
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseInfo forbidden(Exception exception) {
//		return new ResponseInfo(HttpStatus.FORBIDDEN.value() + "", exception.getMessage());//原来的提示：”Subject does not have permission [sys:user:qqq]]“
		return new ResponseInfo(HttpStatus.FORBIDDEN.value() + "", "该用户无此操作权限");//这里改成更直观的提示
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
			UnsatisfiedServletRequestParameterException.class, MethodArgumentTypeMismatchException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseInfo badRequestException(Exception exception) {
		return new ResponseInfo(HttpStatus.BAD_REQUEST.value() + "", exception.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseInfo exception(Throwable throwable) {
		log.error("系统异常", throwable);
		return new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", throwable.getMessage());

	}

}
