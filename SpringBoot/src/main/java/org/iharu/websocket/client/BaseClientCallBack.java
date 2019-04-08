package org.iharu.websocket.client;

import java.io.IOException;
import javax.annotation.PreDestroy;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.web.socket.TextMessage;

public abstract class BaseClientCallBack
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
          getImplLogger().error(ExceptionUtils.getStackTrace(ex));
        }
    }
  
    protected abstract void callback(TextMessage paramTextMessage);
  
}
