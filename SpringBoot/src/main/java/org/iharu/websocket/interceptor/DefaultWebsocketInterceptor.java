/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.websocket.interceptor;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.iharu.web.WebAttributeConstants;
import org.iharu.web.session.entity.SessionEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * WebSocket 拦截器，用于将用户信息从session中存入map，方便后面websocket请求时从map中找到指定的用户session信息
 * @author iHaru
 * https://blog.csdn.net/qq_28988969/article/details/78104850
 * https://dzone.com/articles/spring-boot-based-websocket
 */
public abstract class DefaultWebsocketInterceptor implements HandshakeInterceptor {
    
    protected static final String SESSION_DATA = WebAttributeConstants.SESSION_DATA;

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                    HttpSession session = servletRequest.getServletRequest().getSession();
                    
                    SessionEntity sessionEntity = valid(servletRequest, session);
                    if(sessionEntity == null)
                        return false;
                    
                    attributes.put(SESSION_DATA, sessionEntity);
                    return true;
                }
		return false;
	}
        
        protected abstract SessionEntity valid(ServletServerHttpRequest servletRequest, HttpSession session);

        @Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
	}
}