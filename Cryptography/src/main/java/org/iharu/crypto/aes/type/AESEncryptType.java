/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto.aes.type;

/**
 *
 * @author iHaru
 */
public enum AESEncryptType {
    CFB("AES/CFB/NoPadding", 16),
    GCM("AES/GCM/NoPadding", 12)
    ;
    
    private String name;
    private int ivLength;
    
    AESEncryptType(String name, int ivLength) {
        this.name = name;
        this.ivLength = ivLength;
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getIvLength(){
        return this.ivLength;
    }
    
}
