/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.websocket.util;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.proto.websocket.nonsystem.WebsocketNonSystemProto;
import org.iharu.proto.websocket.system.WebsocketSystemProto;
import org.iharu.type.ResultType;
import org.iharu.type.websocket.WebsocketMessageType;
import org.iharu.type.websocket.WebsocketSystemMessageType;
import org.iharu.util.JsonUtils;
import org.springframework.web.socket.TextMessage;

/**
 *
 * @author iHaru
 */
public class WebsocketUtils {
    
    public static <T> WebsocketProto SystemMessageEncoder(ResultType resultType, WebsocketSystemMessageType systemMessageType, T data) {
        return new WebsocketProto(WebsocketMessageType.SYSTEM, resultType, new WebsocketSystemProto(systemMessageType, data));
    }
    
    public static <T> WebsocketProto NonSystemMessageEncoder(ResultType resultType, String module, T data) {
        if(StringUtils.isBlank(module)){
            return new WebsocketProto(WebsocketMessageType.NON_SYSTEM, resultType, data);
        } else {
            return new WebsocketProto(WebsocketMessageType.NON_SYSTEM, resultType, new WebsocketNonSystemProto(module, data));
        }
    }
    
    public static <T> WebsocketProto NonSystemMessageEncoder(ResultType resultType, T data) {
        return NonSystemMessageEncoder(resultType, null, data);
    }
    
    public static WebsocketProto MessageDecoder(String proto) throws IOException{
        return JsonUtils.json2object(proto, new TypeReference<WebsocketProto>(){});
    }
    
    
}
