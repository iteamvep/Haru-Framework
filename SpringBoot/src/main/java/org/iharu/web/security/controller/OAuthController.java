/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.security.controller;

import javax.servlet.http.HttpSession;
import org.iharu.authorization.util.AuthorizationUtils;
import org.iharu.exception.BaseException;
import org.iharu.proto.web.WebAuthProto;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseAuthorizationType;
import org.iharu.type.ResultType;
import org.iharu.type.error.ErrorType;
import org.iharu.web.WebAttributeConstants;
import org.iharu.web.security.BaseSecurityComponent;
import org.iharu.web.session.entity.SessionEntity;
import org.iharu.web.util.HttpUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author iHaru
 */
@RestController
@RequestMapping(value = {"/oauth","/OAuth"}, produces = "application/json;charset=UTF-8")
public class OAuthController extends BaseSecurityComponent {
    
    /**
     * 
     * @param reqBody
     * @param session
     * @return 
     */
    @PostMapping("/authenticate")
    public WebResponseProto authenticate(@RequestBody String reqBody, HttpSession session) {
        boolean verified = verifyIdentity(reqBody);
        if(verified){
            SessionEntity sessionEntity = new SessionEntity();
            WebAuthProto webAuthProto = new WebAuthProto();
            long timestamp = System.currentTimeMillis();
            String token = AuthorizationUtils.secretTokenGen();
            sessionEntity.setUid(session.getId());
            sessionEntity.setValid_timestamp(timestamp);
            sessionEntity.setBasic_auth_type(BaseAuthorizationType.ADMIN);
            sessionEntity.setToken(token);
            sessionEntity.setOauth(true);
            session.setAttribute(WebAttributeConstants.SESSION_DATA, sessionEntity);
            webAuthProto.setToken(token);
            webAuthProto.setValid_timestamp(timestamp);
            return HttpUtils.CustomResponseGen(ResultType.SUCCESS, webAuthProto);
        } else {
            return HttpUtils.AuthenticationFailed();
        }
    }
    
    /**
     * 
     * @param reqBody
     * @param session
     * @return 
     */
    @PostMapping("/authorize")
    public WebResponseProto authorize(@RequestBody String reqBody, HttpSession session) {
        SessionEntity sessionEntity = (SessionEntity) session.getAttribute(WebAttributeConstants.SESSION_DATA);
        if(sessionEntity == null || !sessionEntity.isOauth()) {
            return HttpUtils.AuthorityInsufficient();
        }
        boolean verified = verifyAuthorization(reqBody, sessionEntity);
        if(verified){
            WebAuthProto webAuthProto = new WebAuthProto();
            long timestamp = System.currentTimeMillis();
            String token = AuthorizationUtils.tokenGen();
            webAuthProto.setVoucher(token);
            webAuthProto.setValid_timestamp(timestamp);
            return HttpUtils.CustomResponseGen(ResultType.SUCCESS, webAuthProto);
        } else {
            return HttpUtils.AuthorityInsufficient();
        }
    }
    
}
