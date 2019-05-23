package org.iharu.websocket.client;

import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
    
    protected final @NotNull String name;
    protected final String description;
    protected final BaseWebsocketClient Instance;
    protected URI url;
    protected BaseClientCallBack callbackImpl;
    protected WebSocketClient webSocketClient;
    protected WebSocketSession webSocketSession;
    protected AtomicInteger retrycount = new AtomicInteger(0);

    public BaseWebsocketClient(String name, String url, BaseClientCallBack callback)
    {
        this(name, null, url, callback);
    }

    public BaseWebsocketClient(String name, String description, String url, BaseClientCallBack callback)
    {
        this.name = name;
        this.description = description;
        this.url = URI.create(url);
        this.callbackImpl = callback;
        this.Instance = this;
    }
    
    public void send(String payload) throws IOException{
        if(!webSocketSession.isOpen()){
            Instance.connect();
            if(!webSocketSession.isOpen()){
                LOG.warn("webSocketSession: {} closed.", name);
                return;
            }
        }
        webSocketSession.sendMessage(new TextMessage(payload));
    }
    
    public void send(byte[] payload) throws IOException{
        if(!webSocketSession.isOpen()){
            Instance.connect();
            if(!webSocketSession.isOpen()){
                LOG.warn("webSocketSession: {} closed.", name);
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
            if ((webSocketSession == null) || (!webSocketSession.isOpen())) {
                webSocketSession = ((WebSocketSession)webSocketClient.doHandshake(new TextWebSocketHandler()
                {
                    @Override
                    public void handleTextMessage(WebSocketSession session, TextMessage message)
                    {
                        callbackImpl.callback(message);
                    }

                    @Override
                    public void afterConnectionEstablished(WebSocketSession session)
                    {
                        BaseWebsocketClient.LOG.info("websocket: {} established connection", name);
                        retrycount = new AtomicInteger(0);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
                      throws Exception
                    {
                        BaseWebsocketClient.LOG.info("websocket: {} connection closed. code: {}, reason:{}", name, status.getCode(), status.getReason());
                        ReconnectContorller.reconnect(Instance);
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
                        LOG.error("websocket: {} TransportError:{}", name, ExceptionUtils.getStackTrace(ex));
                    }
                }, new WebSocketHttpHeaders(), url).get());
            }
            return webSocketSession.isOpen();
        }
        catch (InterruptedException|ExecutionException e)
        {
          LOG.error("websocket: {} Exception while accessing websockets", name, e);
        }
        return false;
    }

    public void close()
      throws IOException
    {
        if ((webSocketSession != null) && (webSocketSession.isOpen())) {
            webSocketSession.close();
        }
    }

    /**
     * @return the retrycount
     */
    public int getRetrycount() {
        return retrycount.get();
    }
}
