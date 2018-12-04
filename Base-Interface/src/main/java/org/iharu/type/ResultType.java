/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.type;

/**
 *
 * @author x5171
 */
public enum ResultType {
    SUCCESS(1),
    FAIL(0),
    ERROR(-1)
    ;
    
    private int code;
    
    ResultType(int code) {
        this.code = code;
    }
    
    public int getCode(){
        return this.code;
    }
    
}
