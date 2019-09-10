/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.websocket.client;

import org.slf4j.Logger;

/**
 *
 * @author iHaru
 */
public abstract class ReconnectCallback {
    
    private String clientID = "";
    
    public void handleException(BaseWebsocketClient wsClient, Throwable ex) {
        getImplLogger().error("Exception while reconnecting websocket: {}", wsClient.getName(), ex);
    }
    
    public boolean isStopReconnect(BaseWebsocketClient wsClient) {
        return !clientID.equals(wsClient.getClientID());
    }
    
    protected abstract Logger getImplLogger();

    /**
     * @return the clientID
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * @param clientID the clientID to set
     */
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
}
