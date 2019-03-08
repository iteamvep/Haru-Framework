/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.websocket.util;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import org.iharu.proto.websocket.WebsocketProto;
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
    
    public static <T> TextMessage StandardMessageEncoder(ResultType resultType, 
            WebsocketMessageType messageType, 
            WebsocketSystemMessageType systemMessageType, 
            T data) {
        WebsocketProto websocketProto = new WebsocketProto();
        websocketProto.setProto_code(resultType);
        websocketProto.setProto_type(messageType);
        WebsocketSystemProto websocketSystemProto = new WebsocketSystemProto();
        websocketSystemProto.setMsg_type(systemMessageType);
        websocketSystemProto.setData(data);
        return new TextMessage(JsonUtils.object2json(websocketSystemProto, null));
    }
    
    public static WebsocketProto StandardMessageDecoder(String proto) throws IOException{
        return JsonUtils.json2object(proto, new TypeReference<WebsocketProto>(){}, null);
    }
    
    
}
