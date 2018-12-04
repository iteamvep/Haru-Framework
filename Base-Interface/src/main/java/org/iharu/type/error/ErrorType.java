/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.type.error;

/**
 *
 * @author x5171
 */
public enum ErrorType {
    HTTP_ERROR(),
    DATABASE_ERROR(),
    INTERNAL_MODULE_ERROR(),
    EXTERNAL_MODULE_ERROR(),
    PARAMETER_ERROR(),
    CONFIGURATION_ERROR(),
    SEMAPHORE_ERROR(),
    SYNCHRONIZATION_ERROR(),
    KERNEL_ERROR()
    ;
    
}
