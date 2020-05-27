/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.traffic;

import java.util.Arrays;
import java.util.List;
import org.iharu.type.ResultType;

/**
 *
 * @author iHaru
 */
public class TrafficWrapper<T> {
    protected boolean success;
    protected ResultType result;
    private T resultValue;
    protected String msg;
    protected int code;
    protected List<Object> params;
    protected RuntimeException exception;
    
    public TrafficWrapper(){
        this.success = true;
        this.result = ResultType.SUCCESS;
    }
    
    public TrafficWrapper(T value){
        this.success = true;
        this.result = ResultType.SUCCESS;
        this.resultValue = value;
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
    
    public TrafficWrapper(int code, String msg){
        this.success = false;
        this.result = ResultType.FAILURE;
        this.msg = msg;
        this.code = code;
    }
    
    public TrafficWrapper(int code, String msg, Object ...params){
        this.success = false;
        this.result = ResultType.FAILURE;
        this.msg = msg;
        this.code = code;
        this.params = Arrays.asList(params);
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
    
    /**
     * @return the resultValue
     */
    public T getResultValue() {
        return resultValue;
    }

    /**
     * @param resultValue the resultValue to set
     */
    public void setResultValue(T resultValue) {
        this.resultValue = resultValue;
    }
}
