/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.websocket.handler;

import java.io.EOFException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketSystemMessageType;
import org.iharu.util.JsonUtils;
import org.iharu.web.WebAttributeConstants;
import org.iharu.web.session.entity.SessionEntity;
import org.iharu.websocket.util.WebsocketUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author iHaru
 * https://blog.csdn.net/Veggiel/article/details/52300093
 * @param <T>
 */
public abstract class DefaultWebsocketHandler<T> extends TextWebSocketHandler {
    
    private static final String SESSION_DATA = WebAttributeConstants.SESSION_DATA;

    abstract protected org.slf4j.Logger GetImplLogger();
    abstract protected Map<String, WebSocketSession> GetUsers();
    
    /**
     * 连接已关闭，移除在Map集合中的记录
     * @param session
     * @param status
     * @throws java.lang.Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        handleClose(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(exception instanceof EOFException)
            return;
        GetImplLogger().error("handleTransportError: {}", ExceptionUtils.getStackTrace(exception));
    }

    /**
     * 连接建立成功之后，记录用户的连接标识，便于后面发信息
     * @param session
     * @throws java.lang.Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserId(session);
        GetImplLogger().debug("user: {} connected", userId);
        if (userId != null) {
            registerUser(userId, session);
            sendConnectedMsg(userId);
        } else {
            GetImplLogger().error("session: {} could not find userid", session);
        }
    }

    /**
     * 处理收到的websocket信息
     * @param session
     * @param message
     */
    @Override
    abstract protected void handleTextMessage(WebSocketSession session, TextMessage message);

     /**
     * 发送信息给指定用户
     * @param <T>
     * @param userId
     * @param payload
     * @return
     */
    public<T> boolean sendMessageToUser(String userId, T payload) {
        if(payload == null)
            return false;
        if (!GetUsers().containsKey(userId)) {
            return false;
        }
        WebSocketSession session = GetUsers().get(userId);

        if (!session.isOpen()) {
            return false;
        }
        TextMessage message;
        if(payload instanceof String){
            message = new TextMessage((String)payload);
        } else if(payload instanceof Integer || payload instanceof Long){
            message = new TextMessage(String.valueOf(payload));
        } else {
            message = new TextMessage(JsonUtils.object2json(payload));
        }
        try {
            session.sendMessage(message);
            return true;
        } catch (IOException e) {
            GetImplLogger().error("user: {}, send message failed. {}", userId, ExceptionUtils.getStackTrace(e));
        }
        return false;
    }
    
    /**
     * 发送信息给指定用户
     * @param userId
     * @param payload
     * @return
     */
    public boolean sendMessageToUser(String userId, byte[] payload) {
        if(payload == null)
            return false;
        if (!GetUsers().containsKey(userId)) {
            return false;
        }
        WebSocketSession session = GetUsers().get(userId);

        if (!session.isOpen()) {
            return false;
        }
        try {
            session.sendMessage(new BinaryMessage(payload));
            return true;
        } catch (IOException e) {
            GetImplLogger().error("user: {}, send message failed. {}", userId, ExceptionUtils.getStackTrace(e));
        }
        return false;
    }

    /**
     * 广播信息
     * @param <T>
     * @param payload
     * @return
     */
    public <T> boolean sendMessageToAllUsers(T payload) {
        if(payload == null)
            return false;
        AtomicBoolean allSendSuccess = new AtomicBoolean(true);
        TextMessage message;
        if(payload instanceof String){
            message = new TextMessage((String)payload);
        } else if(payload instanceof Integer || payload instanceof Long){
            message = new TextMessage(String.valueOf(payload));
        } else {
            message = new TextMessage(JsonUtils.object2json(payload));
        }
        GetUsers().forEach((uid, session) -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                GetImplLogger().error("user: {}, send message failed. {}", uid, ExceptionUtils.getStackTrace(e));
                allSendSuccess.set(false);
            }
        });
        return  allSendSuccess.get();
    }

    /**
     * 广播信息
     * @param payload
     * @return
     */
    public boolean sendMessageToAllUsers(byte[] payload) {
        if(payload == null)
            return false;
        AtomicBoolean allSendSuccess = new AtomicBoolean(true);
        BinaryMessage message = new BinaryMessage(payload);
        GetUsers().forEach((uid, session) -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                GetImplLogger().error("user: {}, send message failed. {}", uid, ExceptionUtils.getStackTrace(e));
                allSendSuccess.set(false);
            }
        });
        return  allSendSuccess.get();
    }

     /**
     * 获取用户标识
     * @param session
     * @return
     */
    protected String getUserId(WebSocketSession session) {
        try {
            SessionEntity sessionEntity = (SessionEntity) session.getAttributes().get(SESSION_DATA);
            if(sessionEntity != null)
                return sessionEntity.getUid();
        } catch (Exception ex) {
            GetImplLogger().error("getUserId: {}", ExceptionUtils.getStackTrace(ex));
        }
        return session.getId();
    }
    
    protected void registerUser(String userId, WebSocketSession session){
        GetUsers().put(userId, session);
    }
    
    protected void unRegisterUser(String userId){
        GetUsers().remove(userId);
    }
    
    protected void handleClose(WebSocketSession session) {
        unRegisterUser(getUserId(session));
        try {
            if(session.isOpen())
                session.close();
        } catch (IOException ex) {
            GetImplLogger().error("handleClose: {}", ExceptionUtils.getStackTrace(ex));
        }
    }
    
    protected void sendConnectedMsg(String userId){
        sendMessageToUser(userId, WebsocketUtils.SystemMessageEncoder(ResultType.SUCCESS, 
                WebsocketSystemMessageType.SYSTEM_INFO, 
                "连接服务器成功"));
    }
    
    protected WebsocketProto proto2object(WebSocketSession session, TextMessage message) {
        return proto2object(getUserId(session), message.getPayload());
    }
    
    protected WebsocketProto proto2object(WebSocketSession session, String proto) {
        return proto2object(getUserId(session), proto);
    }
    
    protected WebsocketProto proto2object(String userId, String proto) {
        try {
            return WebsocketUtils.MessageDecoder(proto);
        } catch (IOException ex) {
            GetImplLogger().error("user: {}, decode proto failed. proto: {}", userId, proto);
            WebsocketProto websocketProto = WebsocketUtils.SystemMessageEncoder(ResultType.FAIL, 
                WebsocketSystemMessageType.PAYLOAD_ERROR, 
                proto);
            sendMessageToUser(userId, websocketProto);
        }
        return null;
    }
    
}
