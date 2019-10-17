/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.auth2.authentication.traffic;

import org.iharu.traffic.TrafficWrapper;
import org.iharu.type.ResultType;
import protobuf.proto.iharu.C2S_LoginProto;

/**
 *
 * @author iHaru
 */
public class LoginWrapper extends TrafficWrapper {
    private C2S_LoginProto proto;
    
    public LoginWrapper(){}
    
    public LoginWrapper(C2S_LoginProto proto){
        this.success = true;
        this.result = ResultType.SUCCESS;
        this.proto = proto;
    }
    
    public LoginWrapper(String msg){
        this.success = false;
        this.result = ResultType.FAILURE;
        this.msg = msg;
    }

    /**
     * @return the proto
     */
    public C2S_LoginProto getProto() {
        return proto;
    }

    /**
     * @param proto the proto to set
     */
    public void setProto(C2S_LoginProto proto) {
        this.proto = proto;
    }
}
