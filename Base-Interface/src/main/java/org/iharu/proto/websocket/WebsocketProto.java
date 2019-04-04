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
 * @author iHaru
 */
public class WebsocketProto<T> {
    
    private WebsocketMessageType proto_type;
    private ResultType proto_code;
    private long timestamp;
    private String sign;
    private T proto_payload;
    
    public WebsocketProto(){}
    
    public WebsocketProto (WebsocketMessageType proto_type, ResultType proto_code, T proto_payload) {
        this.proto_type = proto_type;
        this.proto_code = proto_code;
        this.proto_payload = proto_payload;
    }
    
    public WebsocketProto (WebsocketMessageType proto_type, ResultType proto_code, T proto_payload, long timestamp, String sign) {
        this.proto_type = proto_type;
        this.proto_code = proto_code;
        this.proto_payload = proto_payload;
        this.timestamp = timestamp;
        this.sign = sign;
    }

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

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign the sign to set
     */
    public void setSign(String sign) {
        this.sign = sign;
    }
    
}
