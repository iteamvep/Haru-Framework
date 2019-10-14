/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto.rsa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

/**
 *
 * @author iHaru
 */
public class RSAKeyPairGenerator {
    private final static String KEY_RSA = "RSA";
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public RSAKeyPairGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_RSA);
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    /**
     * 
     * @param pubPath
     * @param priPath
     * @throws NoSuchAlgorithmException
     * @throws IOException 
     */
    public void gen(String pubPath, String priPath) throws NoSuchAlgorithmException, IOException{
        if(pubPath == null)
            writeToFile("RSA/publicKey", Base64.getEncoder().encode(getPublicKey().getEncoded()));
        else 
            writeToFile(pubPath, Base64.getEncoder().encode(getPublicKey().getEncoded()));
        if(priPath == null)
            writeToFile("RSA/privateKey", Base64.getEncoder().encode(getPrivateKey().getEncoded()));
        else
            writeToFile(priPath, Base64.getEncoder().encode(getPrivateKey().getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(getPublicKey().getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(getPrivateKey().getEncoded()));
    }
    
//    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
//        RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();
//        keyPairGenerator.writeToFile("RSA/publicKey", keyPairGenerator.getPublicKey().getEncoded());
//        keyPairGenerator.writeToFile("RSA/privateKey", keyPairGenerator.getPrivateKey().getEncoded());
//        System.out.println(Base64.getEncoder().encodeToString(keyPairGenerator.getPublicKey().getEncoded()));
//        System.out.println(Base64.getEncoder().encodeToString(keyPairGenerator.getPrivateKey().getEncoded()));
//    }
}
