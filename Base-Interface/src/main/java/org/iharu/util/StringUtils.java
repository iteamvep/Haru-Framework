/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

/**
 *
 * @author iHaru
 */
public class StringUtils {
    private StringUtils() { }   

    public static boolean isNullOrEmpty(String s){
        if(s==null || s.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isNullOrWhiteSpace(String s){
        if(s==null || s.trim().isEmpty()){
            return true;
        }
        return false;
    }
}
