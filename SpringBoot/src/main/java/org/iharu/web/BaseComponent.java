/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.type.ResultType;
import org.iharu.web.entity.BaseMessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author iHaru
 */
public class BaseComponent<T> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseComponent.class);
    
    protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;  
    
    
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
//        response.setHeader("Access-Control-Allow-Origin", "*");
    }  

    protected BaseMessageEntity GenBaseResponse(BaseHttpStatus httpStatus, String msg) {
        BaseMessageEntity respondBase = new BaseMessageEntity();
        respondBase.setStatus(httpStatus);
        respondBase.setMsg(msg);
        return respondBase;
    }
    
    protected BaseMessageEntity GenBaseResponse(BaseHttpStatus httpStatus, T data) {
        BaseMessageEntity respondBase = new BaseMessageEntity();
        respondBase.setStatus(httpStatus);
        respondBase.setData(data);
        return respondBase;
    }
    
    protected BaseMessageEntity GenBaseResponse(BaseHttpStatus httpStatus, String msg, T data) {
        BaseMessageEntity respondBase = new BaseMessageEntity();
        respondBase.setStatus(httpStatus);
        respondBase.setMsg(msg);
        respondBase.setData(data);
        return respondBase;
    }
    
}
