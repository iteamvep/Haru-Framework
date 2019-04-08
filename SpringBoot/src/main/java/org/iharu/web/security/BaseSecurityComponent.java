/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.iharu.type.ResultType;
import org.iharu.web.BaseComponent;
import org.iharu.web.WebAttributeConstants;
import org.iharu.web.session.entity.SessionEntity;
import org.iharu.web.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author iHaru
 */
public class BaseSecurityComponent extends BaseComponent {
    private static final Logger LOG = LoggerFactory.getLogger(BaseSecurityComponent.class);
    
    @ModelAttribute  
    @Override
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
//        response.setHeader("Access-Control-Allow-Origin", "127.0.0.1");
        
    }  
    
    /**
     * 检查身份授权，是否有权限
     * @param request
     * @param response 
     */
    @ModelAttribute  
    public void checkAuthorization(HttpServletRequest request, HttpServletResponse response){  
        Assert.notNull(request, "Request must not be null");  
        boolean isLogined = (boolean) (WebUtils.getSessionAttribute(request, "isLogined") == null ? false:WebUtils.getSessionAttribute(request, "isLogined"));
//        this.isLogined = session.getAttribute("isLogined")== null ? false:"true".equals(session.getAttribute("isLogined"));
    }  
    
    /**
     * 检查身份认证，是否登陆
     * @param request
     * @param response 
     */
    @ModelAttribute  
    public void checkAuthentication(HttpServletRequest request, HttpServletResponse response){  
        Assert.notNull(request, "Request must not be null");  
        boolean isLogined = (boolean) (WebUtils.getSessionAttribute(request, "isLogined") == null ? false:WebUtils.getSessionAttribute(request, "isLogined"));
//        this.isLogined = session.getAttribute("isLogined")== null ? false:"true".equals(session.getAttribute("isLogined"));
    } 
    
    protected boolean verifyAuthorization(String reqBody, SessionEntity sessionEntity){
        
        return true;
    }
    
    protected boolean verifyIdentity(String username, String password, String extradata){
        
        return true;
    }
    
    protected boolean verifyIdentity(String reqBody){
        return verifyIdentity("", "", "");
    }
    
    protected boolean logout(SessionEntity sessionEntity){
        return true;
    }
    
}
