package org.iharu.websocket.client;

import org.slf4j.Logger;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;

public abstract class WebsocketClientCallBack
{
  
    protected abstract BaseWebsocketClient getWebsocketClient();
  
    protected abstract Logger getImplLogger();
  
    protected abstract void callback(TextMessage paramTextMessage);
    
    protected abstract void callback(BinaryMessage paramTextMessage);
  
}
