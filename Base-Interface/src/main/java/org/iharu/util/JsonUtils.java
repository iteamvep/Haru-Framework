/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import static com.google.gson.stream.JsonToken.END_DOCUMENT;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.exception.ExceptionUtils;
import static org.iharu.constant.ConstantValue.LINESEPARATOR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author x5171
 * @param <T>
 */
public class JsonUtils<T> {
    private final static  Logger LOG = LoggerFactory.getLogger(JsonUtils.class);
    
    private final static ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }
    
    public static <T> String object2json(T obj, ObjectMapper _objectMapper){
        try {
            if(_objectMapper == null){
                return objectMapper.writeValueAsString(obj);
            } else {
                return _objectMapper.writeValueAsString(obj);
            }
        } catch (JsonProcessingException ex) {
            LOG.error("对象{}尝试转换为JSON时发生错误。错误代码为： {}{}", 
                    obj,
                    LINESEPARATOR,
                    ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
    
    public static <T> String object2json(T obj){
        return object2json(obj, null);
    }
    
    public static <T> T json2object(String json, TypeReference typeReference, ObjectMapper _objectMapper) throws IOException {
        if(json == null) {
            LOG.warn("json2object - input json is empty");
            return null;
        }
        if(_objectMapper == null){
            return objectMapper.readValue(json, typeReference);
        } else {
            return _objectMapper.readValue(json, typeReference);
        }
    }
    
    public static <T> T json2object(String json, TypeReference typeReference) throws IOException {
        return json2object(json, typeReference, null);
    }
    
    public static <T> T json2object(byte[] bytes, TypeReference typeReference, ObjectMapper _objectMapper) throws IOException {
        if(bytes == null) {
            LOG.warn("json2object - input json is empty");
            return null;
        }
        if(_objectMapper == null){
            return objectMapper.readValue(bytes, typeReference);
        } else {
            return _objectMapper.readValue(bytes, typeReference);
        }
    }
    
    public static <T> T json2object(byte[] bytes, TypeReference typeReference) throws IOException {
        return json2object(bytes, typeReference, null);
    }
    
    public static <T> T json2object(String json, Class<?> clz, ObjectMapper _objectMapper) throws IOException {
        if(json == null) {
            LOG.warn("json2object - input json is empty");
            return null;
        }
        if(_objectMapper == null){
            return objectMapper.readValue(json, objectMapper.constructType(clz));
        } else {
            return _objectMapper.readValue(json, _objectMapper.constructType(clz));
        }
    }
    
    public static <T> T json2object(String json, Class<?> clz) throws IOException {
        return json2object(json, clz, null);
    }
    
    public static <T> T json2object(byte[] bytes, Class<?> clz, ObjectMapper _objectMapper) throws IOException {
        if(bytes == null) {
            LOG.warn("json2object - input bytes is empty");
            return null;
        }
        if(_objectMapper == null){
            return objectMapper.readValue(bytes, objectMapper.constructType(clz));
        } else {
            return _objectMapper.readValue(bytes, _objectMapper.constructType(clz));
        }
    }
    
    public static <T> T json2object(byte[] bytes, Class<?> clz) throws IOException {
        return json2object(bytes, clz, null);
    }
    
    public static <T> T json2objectWithoutThrowException(String json, TypeReference typeReference) {
        try{
            return json2object(json, typeReference, null);
        } catch (IOException ex) {
            LOG.info(ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }
    
    public static <T> T json2objectWithoutThrowException(byte[] bytes, TypeReference typeReference) {
        try{
            return json2object(bytes, typeReference, null);
        } catch (IOException ex) {
            LOG.info(ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }
    
    public static <T> T json2objectWithoutThrowException(String json, Class<?> clz) {
        try{
            return json2object(json, clz, null);
        } catch (IOException ex) {
            LOG.info(ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }
    
    public static <T> T json2objectWithoutThrowException(byte[] bytes, Class<?> clz) {
        try{
            return json2object(bytes, clz, null);
        } catch (IOException ex) {
            LOG.info(ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }
    
    public static <T> T jsonnode2object(JsonNode start2Node, Class<T> clz) throws JsonProcessingException {
        return jsonnode2object(start2Node, clz, null);
    }
    
    public static <T> T jsonnode2object(JsonNode start2Node, Class<T> clz, ObjectMapper _objectMapper) throws JsonProcessingException {
        if(_objectMapper == null){
            return objectMapper.treeToValue(start2Node, clz);
        } else {
            return _objectMapper.treeToValue(start2Node, clz);
        }
    }
    
    public static <T> JsonNode object2jsonnode(T obj) throws JsonProcessingException {
        return object2jsonnode(obj, null);
    }
    
    public static <T> JsonNode object2jsonnode(T obj, ObjectMapper _objectMapper) throws JsonProcessingException {
        if(_objectMapper == null){
            return objectMapper.valueToTree(obj);
        } else {
            return _objectMapper.valueToTree(obj);
        }
    }
    
    public static JsonNode json2jsonnode(String json) throws IOException {
        return json2jsonnode(json, null);
    }
    
    public static JsonNode diffObject(Object source, Object target) {
        return JsonDiff.asJson(objectMapper.valueToTree(source), objectMapper.valueToTree(target));
    }
    
    public static JsonNode diffObject(Object source, Object target, ObjectMapper _objectMapper) {
        JsonNode targetNode;
        JsonNode sourceNode;
        if(_objectMapper == null){
            targetNode = objectMapper.valueToTree(target);
            sourceNode = objectMapper.valueToTree(source);
        } else {
            targetNode = _objectMapper.valueToTree(target);
            sourceNode = _objectMapper.valueToTree(source);
        }
        return JsonDiff.asJson(sourceNode, targetNode);
    }
    
    public static<T> byte[] object2bytes(T obj) {
        return StringUtils.StringToByteArray(object2json(obj));
    }
    
    public static JsonNode json2jsonnode(String json, ObjectMapper _objectMapper) throws IOException {
        if(_objectMapper == null){
            return objectMapper.readTree(json);
        } else {
            return _objectMapper.readTree(json);
        }
    }
    
    public static boolean isJsonValid(final String json)
            throws IOException {
        return isJsonValid(new StringReader(json));
    }

    public static boolean isJsonValid(final Reader reader)
            throws IOException {
        return isJsonValid(new JsonReader(reader));
    }

    private static boolean isJsonValid(final JsonReader jsonReader)
            throws IOException {
        try {
            JsonToken token;
            loop:
            while ( (token = jsonReader.peek()) != END_DOCUMENT && token != null ) {
                switch ( token ) {
                case BEGIN_ARRAY:
                    jsonReader.beginArray();
                    break;
                case END_ARRAY:
                    jsonReader.endArray();
                    break;
                case BEGIN_OBJECT:
                    jsonReader.beginObject();
                    break;
                case END_OBJECT:
                    jsonReader.endObject();
                    break;
                case NAME:
                    jsonReader.nextName();
                    break;
                case STRING:
                case NUMBER:
                case BOOLEAN:
                case NULL:
                    jsonReader.skipValue();
                    break;
                case END_DOCUMENT:
                    break loop;
                default:
                    throw new AssertionError(token);
                }
            }
            return true;
        } catch ( final MalformedJsonException ignored ) {
            return false;
        }
    }
}
