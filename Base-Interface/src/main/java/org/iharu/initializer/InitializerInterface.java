/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.initializer;

import java.io.IOException;

/**
 *
 * @author iHaru
 */
public interface InitializerInterface {
    public void initMethod();
    public void destroyMethod();
    
    public void init() throws Exception ;
    
}
