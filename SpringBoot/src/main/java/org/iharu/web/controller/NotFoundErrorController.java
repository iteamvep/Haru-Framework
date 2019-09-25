/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

/**
 * https://www.logicbig.com/tutorials/spring-framework/spring-boot/error-attributes.html
 * @author iHaru
 */
@RestController
public class NotFoundErrorController extends BaseController implements ErrorController {
    private static final String ERROR_PATH=  "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value=ERROR_PATH)
    public WebResponseProto error(HttpServletRequest request, HttpServletResponse response){
         return GenBaseResponse(BaseHttpStatus.FAILURE, HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, WebRequest webRequest, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
    } 

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    } 
    
}
