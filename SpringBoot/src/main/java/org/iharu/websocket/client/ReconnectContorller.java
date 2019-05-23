package org.iharu.websocket.client;

import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReconnectContorller {
  private static final Logger LOG = LoggerFactory.getLogger(ReconnectContorller.class);
  
  public static void reconnect(BaseWebsocketClient wsClient) {
    Executors.newSingleThreadExecutor().submit(() -> {
          try {
            if (wsClient == null) {
              LOG.warn("websocket client:{} not exist.", wsClient.name);
              return;
            } 
            long st = calcReconnectDelay(wsClient.getRetrycount());
            LOG.info("websocket client:{} will try reconnect after {}s.", wsClient.name, st/1000);
            Thread.sleep(st);
            if(wsClient.webSocketSession.isOpen()){
                LOG.info("websocket client:{} reconnected.", wsClient.name);
                return;
            }
            if (wsClient.connect()) {
                LOG.info("websocket client:{} reconnected.", wsClient.name);
            } else {
                LOG.info("websocket client:{} reconnect failed. retrying...", wsClient.name);
                wsClient.retrycount.getAndIncrement();
                reconnect(wsClient);
            } 
          } catch (InterruptedException e) {
            LOG.error("Exception while sending a message", e);
          }
        });
  }
  
  public static long calcReconnectDelay(int retry) {
    if (retry == 0)
      return 2000L; 
    long delay = Math.round(Math.pow(2.0D, retry) * 1000.0D);
    if (delay > 1800000L)
      delay = 1800000L; 
    return delay;
  }
}
