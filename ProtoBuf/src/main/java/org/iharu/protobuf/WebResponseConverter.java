/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import java.nio.ByteBuffer;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.type.BaseHttpStatus;
import org.iharu.util.CalendarUtils;
import org.iharu.util.JsonUtils;
import org.iharu.util.StringUtils;
import org.slf4j.LoggerFactory;
import protobuf.proto.iharu.WebResponseProto;

/**
 *
 * @author iHaru
 */
public class WebResponseConverter {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(WebResponseConverter.class);
    
    public static org.iharu.proto.web.WebResponseProto TransforAndConvert(byte[] bytes){
        if(bytes == null)
            return null;
        try {
            WebResponseProto pb = WebResponseProto.parseFrom(bytes);
            if(pb == null)
                return null;
            org.iharu.proto.web.WebResponseProto webResponseProto = new org.iharu.proto.web.WebResponseProto();
            webResponseProto.setStatus(convertStatus(pb.getStatus()));
            webResponseProto.setMsg(pb.getMsg());
            webResponseProto.setData(pb.getPayload().toByteArray());
        } catch (InvalidProtocolBufferException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
    
    public static org.iharu.proto.web.WebResponseProto TransforAndConvert(ByteBuffer buffer){
        return TransforAndConvert(buffer.array());
    }
    
    public static WebResponseProto Transfor(byte[] bytes){
        try {
            return WebResponseProto.parseFrom(bytes);
        } catch (InvalidProtocolBufferException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
    
    public static WebResponseProto Transfor(ByteBuffer buffer){
        return Transfor(buffer.array());
    }
    
    public static WebResponseProto TransforAndConvert(org.iharu.proto.web.WebResponseProto proto) {
        byte[] data = convertData(proto.getData());
        WebResponseProto websocketWapper = 
                WebResponseProto.newBuilder()
                        .setStatus(convertStatus(proto.getStatus()))
                        .setMsg(proto.getMsg()==null?proto.getStatus().getMsg():proto.getMsg())
                        .setPayload(data==null?ByteString.EMPTY:ByteString.copyFrom(data))
                    .setTimestamp(CalendarUtils.getZonedTimestamp())
                    .setSign(data==null?"":DigestUtils.sha512Hex(data))
                    .build();
        return websocketWapper;
    }
    
    public static WebResponseProto Transfor(BaseHttpStatus status, String msg, byte[] payload){
        WebResponseProto websocketWapper = 
                WebResponseProto.newBuilder()
                        .setStatus(convertStatus(status))
                        .setMsg(msg==null?status.getMsg():msg)
                        .setPayload(payload==null?ByteString.EMPTY:ByteString.copyFrom(payload))
                    .setTimestamp(CalendarUtils.getZonedTimestamp())
                    .setSign(payload==null?"":DigestUtils.sha512Hex(payload))
                    .build();
        return websocketWapper;
    }
    
    public static WebResponseProto Transfor(String msg, byte[] payload){
        return Transfor(BaseHttpStatus.SUCCESS, msg, payload);
    }
    
    public static WebResponseProto.Status convertStatus(BaseHttpStatus status){
        switch(status){
            case SUCCESS:
                return WebResponseProto.Status.SUCCESS;
            case FAILURE:
                return WebResponseProto.Status.FAILURE;
            case ERROR:
                return WebResponseProto.Status.ERROR;
            case UNKNOWN:
                return WebResponseProto.Status.UNKNOWN;  
            default:
                return WebResponseProto.Status.UNRECOGNIZED;
        }
    }
    
    public static BaseHttpStatus convertStatus(WebResponseProto.Status status){
        switch(status){
            case SUCCESS:
                return BaseHttpStatus.SUCCESS;
            case FAILURE:
                return BaseHttpStatus.FAILURE;
            case ERROR:
                return BaseHttpStatus.ERROR;
            case UNKNOWN:
                return BaseHttpStatus.UNKNOWN;  
            default:
                return BaseHttpStatus.UNRECOGNIZED;
        }
    }
    
    public static <T> byte[] convertData(T _data){
        String data = null;
        if(_data instanceof Integer || 
                _data instanceof Long ||
                _data instanceof Boolean ||
                _data instanceof Short ||
                _data instanceof Float ||
                _data instanceof Double ||
                _data instanceof Character||
                _data instanceof Character[]){
            data = String.valueOf(_data);
        } else if(_data instanceof String){
            data = (String) _data;
        } else {
            data = JsonUtils.object2json(_data);
        }
        return ByteString.copyFromUtf8(data).toByteArray();
    }
    
}
