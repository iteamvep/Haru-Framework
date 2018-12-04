/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.util;

import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.ResultType;

/**
 *
 * @author iTeamVEP
 */
public class HttpUtils {
    
    public static WebResponseProto AuthorityInsufficient() {
        return StandardResponseGen(ResultType.FAIL, "Permition denied");
    }
    
    public static WebResponseProto AuthenticationFailed() {
        return StandardResponseGen(ResultType.FAIL, "Authentication failed");
    }
    
    public static WebResponseProto StandardResponseGen(ResultType resultType, String msg) {
        WebResponseProto _response = new WebResponseProto();
        _response.setResponse_code(resultType);
        _response.setResponse_msg(msg);
        return _response;
    }
    
    public static <T> WebResponseProto CustomResponseGen(ResultType resultType, T data) {
        WebResponseProto _response = new WebResponseProto();
        _response.setResponse_code(resultType);
        _response.setResponse_data(data);
        return _response;
    }
    
}
