/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.type;


/**
 *
 * @author iHaru
 */
public enum BaseHttpStatus{
    UNKNOWN("服务器无法处理当前请求", 200),
    ERROR("服务器发生错误", 200),
    FAILURE("失败", 200),
    SUCCESS("成功", 200),
    UNRECOGNIZED("无法辨认当前请求", 200),
    
    OK("", 200),
    Created("", 201),
    Accepted("", 202),
    Not_Modified("", 304),
    Bad_Request("", 400),
    Unauthorized("", 401),
    Payment_Required("", 402),
    Forbidden("", 403),
    Not_Acceptable("", 406),
    Conflict("", 409),
    Unavailable_For_Legal_Reasons ("", 451),
    Internal_Server_Error("", 500),
    Not_Implemented("", 501),
    Service_Unavailable("", 503),
    Network_Authentication_Required("", 511)
    ;
    
    private final String msg;
    private final int httpStatus;
    
    BaseHttpStatus(String msg, int httpStatus) {
        this.msg = msg;
        this.httpStatus = httpStatus;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @return the httpStatus
     */
    public int getHttpStatus() {
        return httpStatus;
    }
    
}
