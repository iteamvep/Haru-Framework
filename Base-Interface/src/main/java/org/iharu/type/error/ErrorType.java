/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.type.error;

/**
 *
 * @author iHaru
 */
public enum ErrorType {
    HTTP_ERROR(),
    DATABASE_ERROR(),
    INTERNAL_MODULE_ERROR(),
    EXTERNAL_MODULE_ERROR(),
    Not_NULL_ERROR(),
    PARAMETER_ERROR(),
    CONFIGURATION_ERROR(),
    OPERATION_ERROR(),
    SYNCHRONIZATION_ERROR(),
    KERNEL_ERROR()
    ;
    
}
