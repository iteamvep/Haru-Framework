/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.websocket.handler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.type.websocket.WebsocketSystemMessageType;
import org.iharu.web.WebAttributeConstants;
import org.iharu.web.session.entity.SessionEntity;
import org.iharu.websocket.util.WebsocketUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author x5171
 */
public abstract class DefaultWebsocketHandler extends TextWebSocketHandler {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultWebsocketHandler.class);
    
    //在线用户列表
    private static final Map<String, WebSocketSession> USERS = new ConcurrentHashMap();
    private static final String SESSION_DATA = WebAttributeConstants.SESSION_DATA;

    /**
     * 连接已关闭，移除在Map集合中的记录
     * @param session
     * @param status
     * @throws java.lang.Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        USERS.remove(getUserUid(session));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        USERS.remove(getUserUid(session));
        LOG.error(ExceptionUtils.getStackTrace(exception));
    }

    /**
     * 连接建立成功之后，记录用户的连接标识，便于后面发信息
     * @param session
     * @throws java.lang.Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("成功建立连接");
        String userId = getUserUid(session);
        LOG.debug("user: {} connected.", userId);
        if (userId != null) {
            USERS.put(userId, session);
            session.sendMessage(connectedMessageGen(userId));
        }
    }

    /**
     * 处理收到的websocket信息
     * @param session
     * @param message
     * @throws java.lang.Exception
     */
    @Override
    abstract protected void handleTextMessage(WebSocketSession session, TextMessage message);

     /**
     * 发送信息给指定用户
     * @param userId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String userId, TextMessage message) {
        if (!USERS.containsKey(userId)) {
            return false;
        }
        WebSocketSession session = USERS.get(userId);

        if (!session.isOpen()) {
            return false;
        }
        try {
            session.sendMessage(message);
            return true;
        } catch (IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        }
        return false;
    }

    /**
     * 广播信息
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<String> userIds = USERS.keySet();
        WebSocketSession session;
        for (String userId : userIds) {
            try {
                session = USERS.get(userId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                LOG.error(ExceptionUtils.getStackTrace(e));
                allSendSuccess = false;
            }
        }
        return  allSendSuccess;
    }

     /**
     * 获取用户标识
     * @param session
     * @return
     */
    private String getUserUid(WebSocketSession session) {
        try {
            SessionEntity sessionEntity = (SessionEntity) session.getAttributes().get(SESSION_DATA);
            return sessionEntity.getUid();
        } catch (Exception e) {
            return null;
        }
    }
    
    private TextMessage connectedMessageGen(String uid){
        return WebsocketUtils.StandardMessageEncoder(ResultType.SUCCESS, 
                WebsocketMessageType.SYSTEM, 
                WebsocketSystemMessageType.SYSTEM_INFO, 
                uid+"\t成功建立socket连接");
    }
    
    private WebsocketProto proto2object(String userId, String proto) {
        try {
            WebsocketProto websocketProto = WebsocketUtils.StandardMessageDecoder(proto);
            return websocketProto;
        } catch (IOException ex) {
            LOG.error("user: {}, decode proto failed. proto: {}", userId, proto);
            TextMessage msg = WebsocketUtils.StandardMessageEncoder(ResultType.FAIL, 
                WebsocketMessageType.SYSTEM, 
                WebsocketSystemMessageType.PAYLOAD_ERROR, 
                proto);
            sendMessageToUser(userId, msg);
        }
        return null;
    }
}
