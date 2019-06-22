/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

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
    
    public static boolean IsCallerLegal(String clazz){
        return GetUpperCaller().getClassName().equals(clazz);
    }
    
    
}
