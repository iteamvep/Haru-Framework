/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.security.controller;

import javax.servlet.http.HttpSession;
import org.iharu.authorization.util.AuthorizationUtils;
import org.iharu.cache.SessionCache;
import org.iharu.proto.web.WebAuthProto;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseAuthorizationType;
import org.iharu.type.BaseHttpStatus;
import org.iharu.type.ResultType;
import org.iharu.web.WebAttributeConstants;
import org.iharu.web.security.BaseSecurityController;
import org.iharu.web.session.entity.SessionEntity;
import org.iharu.web.util.HttpUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author iHaru
 */
@RestController
@RequestMapping(value = {"/admin","/Admin"}, produces = "application/json;charset=UTF-8")
public class LoginController extends BaseSecurityController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(LoginController.class);
    
    @RequestMapping("/urlsignin")
    public WebResponseProto urlAuthentication(
            @RequestParam(value="username", defaultValue="") String username,
            @RequestParam(value="password", defaultValue="") String password,
            @RequestParam(value="extradata", defaultValue="") String extradata,
            HttpSession session
    ) {
        boolean verified = verifyIdentity(username, password, extradata);
        if(verified){
            SessionEntity sessionEntity = new SessionEntity();
            WebAuthProto webAuthProto = new WebAuthProto();
            long timestamp = System.currentTimeMillis();
            String token = AuthorizationUtils.secretTokenGen();
            sessionEntity.setUid(session.getId());
            sessionEntity.setValid_timestamp(timestamp);
            sessionEntity.setBasic_auth_type(BaseAuthorizationType.ADMIN);
            sessionEntity.setToken(token);
            sessionEntity.setOauth(false);
            session.setAttribute(WebAttributeConstants.SESSION_DATA, sessionEntity);
            webAuthProto.setToken(token);
            webAuthProto.setValid_timestamp(timestamp);
            return HttpUtils.GenResponse(BaseHttpStatus.SUCCESS, webAuthProto);
        } else {
            return HttpUtils.AuthenticationFailed();
        }
    }
    
    @RequestMapping("/signin")
    public WebResponseProto baseAuthentication(@RequestBody String reqBody, HttpSession session) {
        boolean verified = verifyIdentity(reqBody);
        if(verified){
            SessionEntity sessionEntity = new SessionEntity();
            WebAuthProto webAuthProto = new WebAuthProto();
            long timestamp = System.currentTimeMillis();
            String token = AuthorizationUtils.secretTokenGen();
            sessionEntity.setUid(session.getId());
            sessionEntity.setValid_timestamp(timestamp);
            sessionEntity.setBasic_auth_type(BaseAuthorizationType.SUPER_ADMIN);
            sessionEntity.setToken(token);
            sessionEntity.setOauth(false);
            session.setAttribute(WebAttributeConstants.SESSION_DATA, sessionEntity);
            webAuthProto.setToken(token);
            webAuthProto.setValid_timestamp(timestamp);
            return HttpUtils.GenResponse(BaseHttpStatus.SUCCESS, webAuthProto);
        } else {
            return HttpUtils.AuthenticationFailed();
        }
    }
    
    @RequestMapping("/signout")
    public WebResponseProto baseAuthentication(HttpSession session) {
        SessionEntity sessionEntity = (SessionEntity) session.getAttribute(WebAttributeConstants.SESSION_DATA);
        if(sessionEntity == null || sessionEntity.isOauth()) {
            return HttpUtils.GenResponse(BaseHttpStatus.FAILURE, "You have not signed in.");
        } else {
            if(logout(sessionEntity)){
                session.removeAttribute(WebAttributeConstants.SESSION_DATA);
            }
            return HttpUtils.GenResponse(BaseHttpStatus.SUCCESS, "Goodbye");
        }
    }
    
}
