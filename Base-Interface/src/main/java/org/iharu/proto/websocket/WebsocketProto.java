/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.proto.websocket;

import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketMessageType;

/**
 *
 * @author iTeamVEP
 */
public class WebsocketProto<T> {
    
    private WebsocketMessageType proto_type;
    private ResultType proto_code;
    private T proto_payload;

    /**
     * @return the proto_type
     */
    public WebsocketMessageType getProto_type() {
        return proto_type;
    }

    /**
     * @param proto_type the proto_type to set
     */
    public void setProto_type(WebsocketMessageType proto_type) {
        this.proto_type = proto_type;
    }

    /**
     * @return the proto_code
     */
    public ResultType getProto_code() {
        return proto_code;
    }

    /**
     * @param proto_code the proto_code to set
     */
    public void setProto_code(ResultType proto_code) {
        this.proto_code = proto_code;
    }

    /**
     * @return the proto_payload
     */
    public T getProto_payload() {
        return proto_payload;
    }

    /**
     * @param proto_payload the proto_payload to set
     */
    public void setProto_payload(T proto_payload) {
        this.proto_payload = proto_payload;
    }
    
}
