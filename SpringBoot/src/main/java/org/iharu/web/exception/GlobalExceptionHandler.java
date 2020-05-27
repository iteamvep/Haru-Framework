/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import static org.iharu.constant.ConstantValue.LINESEPARATOR;
import org.iharu.exception.BaseException;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.type.ResultType;
import org.iharu.web.util.WebResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author iHaru
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    //处理自定义的异常
   @ExceptionHandler(BaseException.class) 
   @ResponseBody
   public WebResponseProto CustomExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        LOG.error("Web - CustomExceptionHandler left message： {}{}", LINESEPARATOR, ExceptionUtils.getStackTrace(ex));
        return WebResponseUtils.GenResponse(BaseHttpStatus.ERROR, "SERVER ERROR");
    }
   
//   @ExceptionHandler(AccessDeniedException.class) 
//   @ResponseBody
//   public WebResponseProto AccessDeniedExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
//        LOG.error("Web - AccessDeniedExceptionHandler left message： {}{}", LINESEPARATOR, request.getSession().getId());
//        return HttpUtils.AuthorityInsufficient();
//    }
   
   //其他未处理的异常
   @ExceptionHandler(Exception.class)
   @ResponseBody
   public Object SystemExceptionHandler(Exception ex){
        if(ex instanceof ClientAbortException){
            LOG.error("Web - SystemExceptionHandler left message： {}", ex.getMessage());
            return null;
        }
        if(ex instanceof IllegalArgumentException)
            return WebResponseUtils.GenResponse(BaseHttpStatus.ERROR, "http body must not be empty");
        if(ex instanceof HttpMessageNotReadableException)
            return WebResponseUtils.GenResponse(BaseHttpStatus.ERROR, "http body must not be empty");
        if(ex instanceof HttpMediaTypeNotAcceptableException)
            return WebResponseUtils.GenResponse(BaseHttpStatus.ERROR, HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
        if(ex instanceof HttpRequestMethodNotSupportedException)
            return WebResponseUtils.GenResponse(BaseHttpStatus.ERROR, HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
        if(ex instanceof RequestRejectedException)
            return WebResponseUtils.GenResponse(BaseHttpStatus.ERROR, HttpStatus.BAD_REQUEST.getReasonPhrase());
        LOG.error("Web - SystemExceptionHandler left an exception", ex);
        return WebResponseUtils.GenResponse(BaseHttpStatus.ERROR, "SERVER ERROR");
   }

}
