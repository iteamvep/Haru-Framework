package org.iharu.websocket.client;

import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReconnectContorller {
    private static final Logger LOG = LoggerFactory.getLogger(ReconnectContorller.class);

    public static void reconnect(BaseWebsocketClient wsClient) {
        Executors.newSingleThreadExecutor().submit(() -> {
            for(;;){
                try {
                    if (wsClient == null) {
                        LOG.warn("websocket client not exist.");
                        break;
                    } 
                    if(wsClient.isShutdown() || wsClient.reconnectCallbackImpl.isStopReconnect(wsClient)) {
                        LOG.info("websocket client:{} - {} has beed shut down, reconnect cancelled", wsClient.getName(), wsClient.getClientID());
                        wsClient.close();
                        break;
                    }
                    if(wsClient.webSocketSession.isOpen()){
                        LOG.info("websocket client:{} - {} reconnected.", wsClient.getName(), wsClient.getClientID());
                        break;
                    }
                    
                    long st = calcReconnectDelay(wsClient.retrycount.get());
                    LOG.info("websocket client:{} - {} will try reconnect after {}s.", wsClient.getName(), wsClient.getClientID(), st/1000);
                    Thread.sleep(st);
                    if (wsClient.connect()) {
                        LOG.info("websocket client:{} - {} reconnected.", wsClient.getName(), wsClient.getClientID());
                        break;
                    } else {
                        LOG.info("websocket client:{} - {} reconnect failed. retrying...", wsClient.getName(), wsClient.getClientID());
                        wsClient.retrycount.getAndIncrement();
                    }
                } catch (InterruptedException ex) {}
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
