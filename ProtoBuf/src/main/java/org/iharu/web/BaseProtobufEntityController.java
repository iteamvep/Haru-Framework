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
public class BaseProtobufEntityController<T> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseProtobufEntityController.class);
    
    protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;  
    
    
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  

    }  

    protected ResponseEntity<Resource> GenBaseResponse(BaseHttpStatus httpStatus, String msg) {
        return GenResponse(WebResponseConverter.Transfor(httpStatus, msg, null));
    }
    
    protected ResponseEntity<Resource> GenResponse(BaseHttpStatus httpStatus, T data) {
        return GenResponse(httpStatus, null, data);
    }
    
    protected ResponseEntity<Resource> GenResponse(BaseHttpStatus httpStatus, String msg, T data) {
        if(data instanceof com.google.protobuf.GeneratedMessageV3){
            return GenResponse(WebResponseConverter.Transfor(httpStatus, msg, ((GeneratedMessageV3) data).toByteArray()));
        } else if(data instanceof byte[]){;
            return GenResponse(WebResponseConverter.Transfor(httpStatus, null, (byte[]) data));
        } else if(data instanceof Byte[]){
            return GenResponse(WebResponseConverter.Transfor(httpStatus, null, (byte[]) ArrayUtils.toPrimitive(data)));
        } else {
            return GenResponse(WebResponseConverter.Transfor(httpStatus, null, WebResponseConverter.convertData(data)));
        }
    }
    
    protected ResponseEntity<Resource> GenResponse(WebResponseProto proto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(
                new InputStreamResource(new ByteArrayInputStream(proto.toByteArray())), 
                headers, 
                HttpStatus.OK);
    }
    
}
