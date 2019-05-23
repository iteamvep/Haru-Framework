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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author iHaru
 * @param <T>
 */
public class BaseController<T> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
    
    protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;  
    
    
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
//        response.setHeader("Access-Control-Allow-Origin", "*");
    }  

    protected WebResponseProto GenBaseResponse(BaseHttpStatus httpStatus, String msg) {
        WebResponseProto responseBody = new WebResponseProto();
        responseBody.setStatus(httpStatus);
        responseBody.setMsg(msg);
        return responseBody;
    }
    
    protected WebResponseProto GenResponse(BaseHttpStatus httpStatus, T data) {
        WebResponseProto responseBody = new WebResponseProto();
        responseBody.setStatus(httpStatus);
        responseBody.setData(data);
        return responseBody;
    }
    
    protected WebResponseProto GenResponse(BaseHttpStatus httpStatus, String msg, T data) {
        WebResponseProto responseBody = new WebResponseProto();
        responseBody.setStatus(httpStatus);
        responseBody.setMsg(msg);
        responseBody.setData(data);
        return responseBody;
    }
    
}
