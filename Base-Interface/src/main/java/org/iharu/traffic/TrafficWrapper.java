/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.traffic;

import org.iharu.type.ResultType;

/**
 *
 * @author iHaru
 */
public class TrafficWrapper {
    protected boolean success;
    protected ResultType result;
    protected String msg;
    protected RuntimeException exception;
    
    public TrafficWrapper(){
        this.success = true;
        this.result = ResultType.SUCCESS;
    }
    
    public TrafficWrapper(boolean rs, String msg){
        this.success = rs;
        if(rs)
            this.result = ResultType.SUCCESS;
        else
            this.result = ResultType.FAILURE;
        this.msg = msg;
    }
    
    public TrafficWrapper(ResultType result, String msg, RuntimeException ex){
        this.success = false;
        this.result = result;
        this.msg = msg;
        this.exception = ex;
    }
    
    public TrafficWrapper(String msg){
        this.success = false;
        this.result = ResultType.FAILURE;
        this.msg = msg;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the result
     */
    public ResultType getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(ResultType result) {
        this.result = result;
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
}
