/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.util;

import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.type.ResultType;

/**
 *
 * @author iHaru
 */
public class WebResponseUtils {
    
    public static WebResponseProto AuthorityInsufficient() {
        return GenResponse(BaseHttpStatus.FAILURE, "Permition denied");
    }
    
    public static WebResponseProto AuthenticationFailed() {
        return GenResponse(BaseHttpStatus.FAILURE, "Authentication failed");
    }
    
    public static WebResponseProto GenResponse(BaseHttpStatus status, String msg) {
        WebResponseProto _response = new WebResponseProto();
        _response.setStatus(status);
        _response.setMsg(msg);
        return _response;
    }
    
    public static <T> WebResponseProto GenResponse(BaseHttpStatus status, T data) {
        WebResponseProto _response = new WebResponseProto();
        _response.setStatus(status);
        _response.setData(data);
        return _response;
    }
    
    public static <T> WebResponseProto GenResponse(BaseHttpStatus status, String msg, T data) {
        WebResponseProto _response = new WebResponseProto();
        _response.setStatus(status);
        _response.setMsg(msg);
        _response.setData(data);
        return _response;
    }
    
}
