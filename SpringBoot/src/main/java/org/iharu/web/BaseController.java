/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web;

import java.util.Arrays;
import java.util.List;
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

    protected static WebResponseProto GenBaseResponse(BaseHttpStatus httpStatus, String msg) {
        WebResponseProto responseBody = new WebResponseProto();
        responseBody.setStatus(httpStatus);
        responseBody.setMsg(msg);
        return responseBody;
    }
    
    protected static <T> WebResponseProto GenResponse(BaseHttpStatus httpStatus, T data) {
        WebResponseProto responseBody = new WebResponseProto();
        responseBody.setStatus(httpStatus);
        responseBody.setData(data);
        return responseBody;
    }
    
    protected static <T> WebResponseProto GenResponse(BaseHttpStatus httpStatus, String msg, T data) {
        WebResponseProto responseBody = new WebResponseProto();
        responseBody.setStatus(httpStatus);
        responseBody.setMsg(msg);
        responseBody.setData(data);
        return responseBody;
    }
    
    protected static <T> WebResponseProto GenResponse(BaseHttpStatus httpStatus, int code, String msg, T data) {
        WebResponseProto responseBody = new WebResponseProto();
        responseBody.setStatus(httpStatus);
        responseBody.setCode(code);
        responseBody.setMsg(msg);
        responseBody.setData(data);
        return responseBody;
    }
    
    protected static <T> WebResponseProto GenResponse(BaseHttpStatus httpStatus, int code, List<Object> params, String msg, T data) {
        WebResponseProto responseBody = new WebResponseProto();
        responseBody.setStatus(httpStatus);
        responseBody.setCode(code);
        responseBody.setParams(params);
        responseBody.setMsg(msg);
        responseBody.setData(data);
        return responseBody;
    }
    
    protected static <T> WebResponseProto GenResponse(BaseHttpStatus httpStatus, int code, String msg, T data, Object ...params) {
        WebResponseProto responseBody = new WebResponseProto();
        responseBody.setStatus(httpStatus);
        responseBody.setCode(code);
        responseBody.setParams(Arrays.asList(params));
        responseBody.setMsg(msg);
        responseBody.setData(data);
        return responseBody;
    }
    
}
