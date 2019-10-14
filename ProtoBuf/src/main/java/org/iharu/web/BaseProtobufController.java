/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.ArrayUtils;
import org.iharu.protobuf.WebResponseConverter;
import org.iharu.type.BaseHttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import protobuf.proto.iharu.WebResponseProto;

/**
 *
 * @author iHaru
 * @param <T>
 */
public class BaseProtobufController<T> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseProtobufController.class);
    
    protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;  
    
    
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  

    }  

    protected WebResponseProto GenBaseResponse(BaseHttpStatus httpStatus, String msg) {
        return WebResponseConverter.Transfor(httpStatus, msg, null);
    }
    
    protected WebResponseProto GenResponse(BaseHttpStatus httpStatus, T data) {
        return GenResponse(httpStatus, null, data);
    }
    
    protected WebResponseProto GenResponse(BaseHttpStatus httpStatus, String msg, T data) {
        if(data instanceof byte[]){
            return WebResponseConverter.Transfor(httpStatus, null, (byte[]) data);
        } else if(data instanceof Byte[]){
            return WebResponseConverter.Transfor(httpStatus, null, (byte[]) ArrayUtils.toPrimitive(data));
        } else {
            return WebResponseConverter.Transfor(httpStatus, null, WebResponseConverter.convertData(data));
        }
    }
    
}
