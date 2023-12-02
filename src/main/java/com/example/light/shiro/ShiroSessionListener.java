package com.example.light.shiro;

import com.example.light.common.UserConstants;
import com.example.light.common.UserUtil;
import com.example.light.config.WebSocketServer;
import com.example.light.dto.curUserDto;
import org.apache.shiro.session.SessionListener;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;/*** 配置session监听*/

public class ShiroSessionListener implements SessionListener {



    @Override
    public void onStart(Session session) {
        // 会话创建
        System.out.println("我创建了");

    }
    /**
     * 退出会话时触发
     * @param session
     */
    @Override
    public void onStop(Session session) {
        // 会话退出
        System.out.println("我退出了");
    }
    /**
     * 会话过期时触发
     * @param session
     */
    @Override
    public void onExpiration(Session session) {
        // 会话过期
        System.out.println("我过期了");
        try {
//            WebSocketServer.sendInfo("您长时间没有操作，请重新登录！！！",session.getId().toString());


            WebSocketServer.sendInfo("您长时间没有操作，请重新登录！！！",session.getId().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
/**
 *
 * // 会话过期弹出框
 * function admin_del(msg) {
 *   layer.alert(msg, {
 *     title: '系统提示',
 *     skin: 'layui-layer-lan',
 *     closeBtn: 0
 *   }, function(index) {
 *     layer.close(index);
 *     location.href = "/logout";
 *   });
 * }
 *
 * // 获取当前会话的ID
 * function getSessionId() {
 *   var c_name = 'JSESSIONID';
 *   c_start = document.cookie.indexOf(c_name + "=");
 *   if (c_start != -1) {
 *     c_start = c_start + c_name.length + 1;
 *     c_end = document.cookie.indexOf(";", c_start);
 *     if (c_end == -1) c_end = document.cookie.length;
 *     return unescape(document.cookie.substring(c_start, c_end));
 *   } else {
 *     // 手动从后台传来sessionid
 *     var JSESSIONID = $('#JSESSIONID').val();
 *     return JSESSIONID;
 *   }
 * }
 *
 * // 获取当前网址和主机地址的相关信息
 * var curWwwPath = window.document.location.href;
 * var pathName = window.document.location.pathname;
 * var pos = curWwwPath.indexOf(pathName);
 * var localhostPaht = curWwwPath.substring(0, pos);
 *
 * var socket;
 * if (typeof(WebSocket) == "undefined") {
 *   console.log("您的浏览器不支持WebSocket");
 * } else {
 *   console.log("您的浏览器支持WebSocket");
 *   console.log(getSessionId());
 *
 *   // 实现化WebSocket对象，指定要连接的服务器地址与端口，建立连接
 *   var socketUrl = localhostPaht + "/imserver/" + getSessionId();
 *   socketUrl = socketUrl.replace("https", "ws").replace("http", "ws");
 *
 *   if (socket != null) {
 *     socket.close();
 *     socket = null;
 *   }
 *
 *   socket = new WebSocket(socketUrl);
 *
 *   // 打开事件
 *   socket.onopen = function() {
 *     console.log("websocket已打开");
 *     // socket.send("这是来自客户端的消息" + location.href + new Date());
 *   };
 *
 *   // 获得消息事件
 *   socket.onmessage = function(msg) {
 *     console.log(msg.data);
 *     admin_del(msg.data);
 *     // 发现消息进入，开始处理前端触发逻辑
 *   };
 *
 *   // 关闭事件
 *   socket.onclose = function() {
 *     console.log("websocket已关闭");
 *   };
 *
 *   // 发生了错误事件
 *   socket.onerror = function() {
 *     console.log("websocket发生了错误");
 *   };
 * }
 */