/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.exception;

import org.iharu.type.error.ErrorType;

/**
 *
 * @author iHaru
 */
public class BaseException extends RuntimeException {
    
    private final ErrorType errorType;
    private String module;
    
    public BaseException(ErrorType errorType, String module, String msg, Throwable sourceException) {
        super(msg, sourceException);
        this.errorType = errorType;
        this.module = module;
    }
    
    public BaseException(ErrorType errorType, String module, String msg) {
        super(msg);
        this.errorType = errorType;
        this.module = module;
    }
    
    public BaseException(ErrorType errorType, String module) {
        this.errorType = errorType;
        this.module = module;
    }
    
    public BaseException(ErrorType errorType, Throwable sourceException) {
        super(sourceException);
        this.errorType = errorType;
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
     * @return the module
     */
    public String getModule() {
        return module;
    }
    
}
