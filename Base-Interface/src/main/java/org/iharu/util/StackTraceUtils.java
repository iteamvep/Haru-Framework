/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class StackTraceUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(StackTraceUtils.class);
    
    public static StackTraceElement GetUpperCaller() {
        for(int i=2;;i++){
            if(!Thread.currentThread().getStackTrace()[i].getClassName().equals(StackTraceUtils.class.getName()))
                return (StackTraceElement)Thread.currentThread().getStackTrace()[i+1]; 
        }
    }
    
    public static boolean IsCallerLegal(String clazz, String method){
        StackTraceElement stack = GetUpperCaller();
        return (stack.getClassName().equals(clazz) && stack.getMethodName().equals(method));
    }
    
    public static boolean IsCallerLegal(List<String[]> list){
        StackTraceElement stack = GetUpperCaller();
        for(String[] caller:list){
            if(caller[1] != null){
                if (stack.getClassName().equals(caller[0]) && stack.getMethodName().equals(caller[1]))
                    return true;
            } else {
                if (stack.getClassName().equals(caller[0]))
                    return true;
            }
        }
        return false;
    }
    
    public static boolean IsCallerLegal(String clazz){
        return GetUpperCaller().getClassName().equals(clazz);
    }
    
    
}
