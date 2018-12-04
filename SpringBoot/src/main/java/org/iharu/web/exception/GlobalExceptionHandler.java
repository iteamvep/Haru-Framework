/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.exception.BaseException;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.ResultType;
import static org.iharu.util.BaseConstantValue.LINESEPARATOR;
import org.iharu.web.controller.DefaultGlobalController;
import org.iharu.web.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author iTeamVEP
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    //处理自定义的异常
   @ExceptionHandler(BaseException.class) 
   @ResponseBody
   public WebResponseProto CustomExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        LOG.error("Web - CustomExceptionHandler left message： {}{}", LINESEPARATOR, ExceptionUtils.getStackTrace(ex));
        return HttpUtils.StandardResponseGen(ResultType.ERROR, "服务器内部发生错误");
    }
   
   //其他未处理的异常
   @ExceptionHandler(Exception.class)
   @ResponseBody
   public Object SystemExceptionHandler(Exception ex){
      LOG.error("Web - SystemExceptionHandler left message： {}{}", LINESEPARATOR, ExceptionUtils.getStackTrace(ex));
        return HttpUtils.StandardResponseGen(ResultType.ERROR, "服务器内部发生错误");
   }

}
