/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import static org.iharu.constant.ConstantValue.LINESEPARATOR;
import org.iharu.exception.BaseException;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.type.ResultType;
import org.iharu.web.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        return HttpUtils.GenResponse(BaseHttpStatus.ERROR, "SERVER ERROR");
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
      LOG.error("Web - SystemExceptionHandler left message： {}{}", LINESEPARATOR, ExceptionUtils.getStackTrace(ex));
        return HttpUtils.GenResponse(BaseHttpStatus.ERROR, "SERVER ERROR");
   }

}
