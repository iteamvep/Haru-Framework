/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.proto.web;

import org.iharu.type.ResultType;

/**
 *
 * @author iTeamVEP
 */
public class WebResponseProto<T> {
    
    private ResultType response_code;
    private String response_msg;
    private T response_data;

    /**
     * @return the response_code
     */
    public ResultType getResponse_code() {
        return response_code;
    }

    /**
     * @param response_code the response_code to set
     */
    public void setResponse_code(ResultType response_code) {
        this.response_code = response_code;
    }

    /**
     * @return the response_msg
     */
    public String getResponse_msg() {
        return response_msg;
    }

    /**
     * @param response_msg the response_msg to set
     */
    public void setResponse_msg(String response_msg) {
        this.response_msg = response_msg;
    }

    /**
     * @return the response_data
     */
    public T getResponse_data() {
        return response_data;
    }

    /**
     * @param response_data the response_data to set
     */
    public void setResponse_data(T response_data) {
        this.response_data = response_data;
    }
    
}
