package com.example.light.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 错误页面配置
 *
 * 继承错误页面注册器 ErrorPageRegistrar
 */
@Configuration
public class ErrorConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        /**
         * 配置错误页面
         *
         * ErrorPage 有两个参数
         * 参数1 响应状态码  NOT_FOUND 404  INTERNAL_SERVER_ERROR 500
         * 参数2 出现响应状态码的时候的跳转路径  可以自定义跳转路径
         */
        //404
        ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        //500
        ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");

        /**
         * 将ErrorPage 注册到注册器中
         */
        registry.addErrorPages(error404,error500);

    }
}