/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web;

import com.google.protobuf.GeneratedMessageV3;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.ArrayUtils;
import org.iharu.protobuf.WebResponseConverter;
import org.iharu.type.BaseHttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import protobuf.proto.iharu.WebResponseProto;

/**
 *
 * @author iHaru
 * @param <T>
 */
public class BaseProtobufBytesController<T> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseProtobufBytesController.class);
    
    protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;  
    
    
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  

    }  
    
    protected byte[] GenBaseResponse(BaseHttpStatus httpStatus, String msg) {
        return WebResponseConverter.Transfor(httpStatus, msg, null).toByteArray();
    }
    
    protected byte[] GenResponse(BaseHttpStatus httpStatus, T data) {
        return GenResponse(httpStatus, null, data);
    }
    
    //produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    //@ResponseBody 
    protected byte[] GenResponse(BaseHttpStatus httpStatus, String msg, T data) {
        if(data instanceof com.google.protobuf.GeneratedMessageV3){
            return WebResponseConverter.Transfor(httpStatus, msg, ((GeneratedMessageV3) data).toByteArray()).toByteArray();
        } else if(data instanceof byte[]){
            return WebResponseConverter.Transfor(httpStatus, msg, (byte[]) data).toByteArray();
        } else if(data instanceof Byte[]){
            return WebResponseConverter.Transfor(httpStatus, msg, (byte[]) ArrayUtils.toPrimitive(data)).toByteArray();
        } else {
            return WebResponseConverter.Transfor(httpStatus, msg, WebResponseConverter.convertData(data)).toByteArray();
        }
    }
    
}
