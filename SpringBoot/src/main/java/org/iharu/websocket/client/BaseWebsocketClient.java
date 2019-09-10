package org.iharu.websocket.client;

import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import org.iharu.util.CommontUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class BaseWebsocketClient
{
    private static final Logger LOG = LoggerFactory.getLogger(BaseWebsocketClient.class);
    
    private boolean isReconnecting = false;
    private boolean shutdown = false;
    private String clientID = null;
    AtomicInteger retrycount = new AtomicInteger(0);
    protected final @NotNull String name;
    protected final String description;
    protected final HashMap<String, String> headers;
    protected final BaseWebsocketClient instance;
    protected final URI url;
    protected final WebsocketClientCallBack clientCallbackImpl;
    protected final ReconnectCallback reconnectCallbackImpl;
    protected WebSocketClient webSocketClient;
    protected WebSocketSession webSocketSession;

    public BaseWebsocketClient(String name, String url, WebsocketClientCallBack callback)
    {
        this(name, null, null, url, callback, null);
    }
    
    public BaseWebsocketClient(String name, HashMap headers, String url, WebsocketClientCallBack callback)
    {
        this(name, null, headers, url, callback, null);
    }
    
    public BaseWebsocketClient(String name, String url, WebsocketClientCallBack callback, ReconnectCallback reconnectCallbackImpl)
    {
        this(name, null, null, url, callback, reconnectCallbackImpl);
    }
    
    public BaseWebsocketClient(String name, HashMap headers, String url, WebsocketClientCallBack callback, ReconnectCallback reconnectCallbackImpl)
    {
        this(name, null, headers, url, callback, reconnectCallbackImpl);
    }

    public BaseWebsocketClient(String name, String description, HashMap headers, String url, WebsocketClientCallBack callback, ReconnectCallback reconnectCallbackImpl)
    {
        this.name = name;
        this.description = description;
        this.headers = headers;
        this.url = URI.create(url);
        this.clientCallbackImpl = callback;
        this.reconnectCallbackImpl = reconnectCallbackImpl == null ? new ReconnectCallback(){
            @Override
            public Logger getImplLogger() {
                return LOG;
            }
        }:reconnectCallbackImpl;
        this.instance = this;
    }
    
    public void send(String payload) throws IOException{
        if(!webSocketSession.isOpen()){
            instance.connect();
            if(!webSocketSession.isOpen()){
                LOG.warn("webSocketSession: {} closed.", getName());
                return;
            }
        }
        webSocketSession.sendMessage(new TextMessage(payload));
    }
    
    public void send(byte[] payload) throws IOException{
        if(!webSocketSession.isOpen()){
            instance.connect();
            if(!webSocketSession.isOpen()){
                LOG.warn("webSocketSession: {} closed.", getName());
                return;
            }
        }
        webSocketSession.sendMessage(new BinaryMessage(payload));
    }

    public boolean connect()
    {
        try
        {
            if (webSocketClient == null) {
                webSocketClient = new StandardWebSocketClient();
            }
            WebSocketHttpHeaders _headers = new WebSocketHttpHeaders();
            if(headers != null) {
                headers.forEach((k, v) -> {
                    _headers.add(k, v);
                });
            }
            if ((webSocketSession == null) || (!webSocketSession.isOpen())) {
                webSocketSession = ((WebSocketSession)webSocketClient.doHandshake(new TextWebSocketHandler() {
                    @Override
                    public void handleTextMessage(WebSocketSession session, TextMessage message)
                    {
                        clientCallbackImpl.callback(message);
                    }
                    
                    @Override
                    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message)
                    {
                        clientCallbackImpl.callback(message);
                    }

                    @Override
                    public void afterConnectionEstablished(WebSocketSession session)
                    {
                        BaseWebsocketClient.LOG.info("websocket: {} established connection", getName());
                        retrycount = new AtomicInteger(0);
                        isReconnecting = false;
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
                      throws Exception
                    {
                        if(shutdown)
                            return;
                        if(isReconnecting == true)
                            return;
                        isReconnecting = true;
                        BaseWebsocketClient.LOG.info("websocket: {} connection closed. code: {}, reason:{}", getName(), status.getCode(), status.getReason());
                        ReconnectContorller.reconnect(instance);
                    }

                    @Override
                    public void handleTransportError(WebSocketSession session, Throwable ex)
                      throws Exception
                    {
                        if (session.isOpen()) {
                          session.close();
                        }
                        if(ex instanceof EOFException)
                            return;
                        LOG.error("websocket: {} Exception while transport data", getName(), ex);
                    }
                }, _headers, url).get());
            }
            if(webSocketSession.isOpen()){
                shutdown = false;
                clientID = CommontUtils.GenUUID();
                reconnectCallbackImpl.setClientID(clientID);
                return true;
            }
        }
        catch (ExecutionException ex)
        {
            reconnectCallbackImpl.handleException(this, ex);
        } catch (InterruptedException ex) {
            
        }
        LOG.debug("websocket: {} connect failed", getName());
        return false;
    }

    public void close() {
        if(shutdown)
            return;
        shutdown = true;
        if ((webSocketSession != null) && (webSocketSession.isOpen())) {
            try {
                webSocketSession.close();
            } catch (IOException ex) {
                
            }
        }
    }
    
    @PreDestroy
    public void destroyMethod()
    {
        close();
    }

    /**
     * @return the retrycount
     */
    public int getRetrycount() {
        return retrycount.get();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the shutdown
     */
    public boolean isShutdown() {
        return shutdown;
    }

    /**
     * @return the clientID
     */
    public String getClientID() {
        return clientID;
    }
}
