/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.proto.web;

import java.util.List;
import org.iharu.type.BaseHttpStatus;

/**
 *
 * @author iHaru
 */
public class WebResponseProto<T> {
    
    private BaseHttpStatus status;
    private int code;
    private List<Object> params;
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

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the params
     */
    public List<Object> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(List<Object> params) {
        this.params = params;
    }

}
