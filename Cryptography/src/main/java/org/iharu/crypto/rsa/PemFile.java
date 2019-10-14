/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto.rsa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.Key;
 
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class PemFile {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(PemFile.class);
    
    private final PemObject pemObject;
	
    public PemFile (Key key, String description) {
	this.pemObject = new PemObject(description, key.getEncoded());
    }
	
    public void write(String filename) throws FileNotFoundException, IOException {
	try (PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(filename)))) {
            pemWriter.writeObject(this.pemObject);
	}
    }
        
}
