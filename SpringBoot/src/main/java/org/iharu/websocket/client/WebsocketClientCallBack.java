package org.iharu.websocket.client;

import java.io.IOException;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;

public abstract class WebsocketClientCallBack
{
  
    protected abstract BaseWebsocketClient getWebsocketClient();
  
    protected abstract Logger getImplLogger();
  
    @PreDestroy
    public void destroyMethod()
    {
        try
        {
            if (getWebsocketClient() != null) {
              getWebsocketClient().close();
            }
        }
        catch (IOException ex)
        {
          getImplLogger().error("websocket client {} close error", getWebsocketClient().getName(), ex);
        }
    }
  
    protected abstract void callback(TextMessage paramTextMessage);
    
    protected abstract void callback(BinaryMessage paramTextMessage);
  
}
