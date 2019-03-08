/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.ResultType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author iHaru
 */
@Component
public class BaseComponent {
    
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
//        response.setHeader("Access-Control-Allow-Origin", "*");
    }  
    
}
