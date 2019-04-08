/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.entity;

import org.iharu.type.BaseHttpStatus;

/**
 *
 * @author iHaru
 */

public class BaseMessageEntity<T> {
    private BaseHttpStatus status;
    private String msg;
    private T data;

    public BaseMessageEntity(){}
    
    public BaseMessageEntity(BaseHttpStatus status, String msg, T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    
    public BaseMessageEntity(BaseHttpStatus status, String msg){
        this.status = status;
        this.msg = msg;
    }
    
    /**
     * @return the status
     */
    public BaseHttpStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(BaseHttpStatus status) {
        this.status = status;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
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
