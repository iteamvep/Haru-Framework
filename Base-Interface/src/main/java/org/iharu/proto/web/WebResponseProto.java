/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.proto.web;

import org.iharu.type.BaseHttpStatus;
import org.iharu.type.ResultType;

/**
 *
 * @author iHaru
 */
public class WebResponseProto<T> {
    
    private BaseHttpStatus status;
    private String msg;
    private T data;

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
