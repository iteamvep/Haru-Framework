/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.websocket.util;

import com.fasterxml.jackson.core.type.TypeReference;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.iharu.exception.BaseException;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.proto.websocket.system.WebsocketSystemProto;
import org.iharu.type.ResultType;
import org.iharu.type.error.ErrorType;
import org.iharu.type.websocket.WebsocketSystemMessageType;
import org.iharu.util.JsonUtils;

/**
 *
 * @author iHaru
 */
public class WebsocketUtils {
    
    public static <T> WebsocketProto SystemMessageEncoder(ResultType resultType, WebsocketSystemMessageType systemMessageType, String data) {
        return new WebsocketProto(resultType, new WebsocketSystemProto(systemMessageType, data));
    }
    
    public static <T> WebsocketProto NonSystemMessageEncoder(ResultType resultType, @NotNull String module, T data) {
        if(StringUtils.isBlank(module))
            throw new BaseException(ErrorType.PARAMETER_ERROR, "module 不能为空");
        return new WebsocketProto(resultType, module, JsonUtils.object2json(data));
    }
    
    public static WebsocketProto MessageDecoder(String proto) throws IOException {
        return JsonUtils.json2object(proto, new TypeReference<WebsocketProto>(){});
    }
    
}
