/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.proto.websocket.nonsystem;

/**
 *
 * @author iHaru
 */
public class WebsocketNonSystemProto<T> {

    private String module;
    private T data;
    
    public WebsocketNonSystemProto(){}
    
    public WebsocketNonSystemProto(String module, T data) {
        this.module = module;
        this.data = data;
    }

    /**
     * @return the module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }


}
