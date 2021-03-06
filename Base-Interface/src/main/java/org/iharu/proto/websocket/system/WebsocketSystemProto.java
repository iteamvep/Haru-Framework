/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.proto.websocket.system;

import org.iharu.type.websocket.WebsocketSystemMessageType;

/**
 *
 * @author iHaru
 */
public class WebsocketSystemProto<T> {
    private WebsocketSystemMessageType msg_type;
    private String data;
    
    public WebsocketSystemProto(){}
    
    public WebsocketSystemProto (WebsocketSystemMessageType msg_type, String data) {
        this.msg_type = msg_type;
        this.data = data;
    }

    /**
     * @return the msg_type
     */
    public WebsocketSystemMessageType getMsg_type() {
        return msg_type;
    }

    /**
     * @param msg_type the msg_type to set
     */
    public void setMsg_type(WebsocketSystemMessageType msg_type) {
        this.msg_type = msg_type;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }
    
}
