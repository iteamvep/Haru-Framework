/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.proto.websocket;

import org.iharu.proto.websocket.system.WebsocketSystemProto;
import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.util.JsonUtils;

/**
 *
 * @author iHaru
 * @param <T>
 */
public class WebsocketProto<T> {
    
    protected ResultType proto_code;
    protected String proto_payload;
    protected String proto_module;
    private String proto_sender;
    private String proto_recipient;
    protected WebsocketMessageType proto_type;
    protected long timestamp;
    protected String sign;
    
    public WebsocketProto(){}
    
    public WebsocketProto (ResultType proto_code, String proto_payload) {
        this.proto_type = WebsocketMessageType.SYSTEM;
        this.proto_code = proto_code;
        this.proto_payload = proto_payload;
    }
    
    public WebsocketProto (ResultType proto_code, String proto_payload, long timestamp, String sign) {
        this.proto_type = WebsocketMessageType.SYSTEM;
        this.proto_code = proto_code;
        this.proto_payload = proto_payload;
        this.timestamp = timestamp;
        this.sign = sign;
    }
    
    public WebsocketProto (ResultType proto_code, WebsocketSystemProto proto_payload) {
        this.proto_type = WebsocketMessageType.SYSTEM;
        this.proto_code = proto_code;
        this.proto_payload = JsonUtils.object2json(proto_payload);
    }
    
    public WebsocketProto (String proto_payload) {
        this.proto_type = WebsocketMessageType.SYSTEM;
        this.proto_code = ResultType.SUCCESS;
        this.proto_payload = proto_payload;
    }
    
    public WebsocketProto (ResultType proto_code, String proto_module, String proto_payload) {
        this.proto_type = WebsocketMessageType.NON_SYSTEM;
        this.proto_code = proto_code;
        this.proto_module = proto_module;
        this.proto_payload = proto_payload;
    }
    
    public WebsocketProto (ResultType proto_code, String proto_module, String proto_sender, String proto_recipient, String proto_payload) {
        this.proto_type = WebsocketMessageType.NON_SYSTEM;
        this.proto_code = proto_code;
        this.proto_module = proto_module;
        this.proto_sender = proto_sender;
        this.proto_recipient = proto_recipient;
        this.proto_payload = proto_payload;
    }
    
    public WebsocketProto (ResultType proto_code, String proto_module, String proto_sender, String proto_recipient, String proto_payload, long timestamp, String sign) {
        this.proto_type = WebsocketMessageType.NON_SYSTEM;
        this.proto_code = proto_code;
        this.proto_module = proto_module;
        this.proto_sender = proto_sender;
        this.proto_recipient = proto_recipient;
        this.proto_payload = proto_payload;
        this.timestamp = timestamp;
        this.sign = sign;
    }
    
    public WebsocketProto (String proto_module, String proto_payload) {
        this.proto_type = WebsocketMessageType.NON_SYSTEM;
        this.proto_code = ResultType.SUCCESS;
        this.proto_module = proto_module;
        this.proto_payload = proto_payload;
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

    /**
     * @return the proto_payload
     */
    public String getProto_payload() {
        return proto_payload;
    }

    /**
     * @param proto_payload the proto_payload to set
     */
    public void setProto_payload(String proto_payload) {
        this.proto_payload = proto_payload;
    }

    /**
     * @return the proto_module
     */
    public String getProto_module() {
        return proto_module;
    }

    /**
     * @param proto_module the proto_module to set
     */
    public void setProto_module(String proto_module) {
        this.proto_module = proto_module;
    }

    /**
     * @return the proto_sender
     */
    public String getProto_sender() {
        return proto_sender;
    }

    /**
     * @param proto_sender the proto_sender to set
     */
    public void setProto_sender(String proto_sender) {
        this.proto_sender = proto_sender;
    }

    /**
     * @return the proto_recipient
     */
    public String getProto_recipient() {
        return proto_recipient;
    }

    /**
     * @param proto_recipient the proto_recipient to set
     */
    public void setProto_recipient(String proto_recipient) {
        this.proto_recipient = proto_recipient;
    }
    
}
