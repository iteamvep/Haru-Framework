/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.exception;

import org.iharu.type.error.ErrorType;

/**
 *
 * @author iTeamVEP
 */
public class BaseException extends RuntimeException {
    
    private final ErrorType errorType;
    private String module;
    private String msg;
    private Throwable sourceException;
    
    public BaseException(ErrorType errorType, String module, String msg, Throwable sourceException) {
        this.errorType = errorType;
        this.module = module;
        this.msg = msg;
        this.sourceException = sourceException;
    }
    
    public BaseException(ErrorType errorType, String module, String msg) {
        this.errorType = errorType;
        this.module = module;
        this.msg = msg;
    }
    
    public BaseException(ErrorType errorType, String module) {
        this.errorType = errorType;
        this.module = module;
    }
    
    public BaseException(ErrorType errorType, Throwable sourceException) {
        this.errorType = errorType;
        this.sourceException = sourceException;
    }
    
    public BaseException(ErrorType errorType) {
        this.errorType = errorType;
    }

    /**
     * @return the errorType
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @return the sourceException
     */
    public Throwable getSourceException() {
        return sourceException;
    }

    /**
     * @return the module
     */
    public String getModule() {
        return module;
    }
    
}
