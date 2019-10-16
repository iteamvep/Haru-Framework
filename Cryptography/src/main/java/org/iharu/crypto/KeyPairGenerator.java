/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 * https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html
 */
public class KeyPairGenerator {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(KeyPairGenerator.class);
    
    private KeyPair keyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String algorithm;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    private KeyPairGenerator(){}
    
    /**
     * 
     * @param algorithm
     * @param keyLen
     * @return 
     * @throws NoSuchAlgorithmException 
     * @throws java.security.NoSuchProviderException 
     */
    public static KeyPairGenerator getInstance(String algorithm, int keyLen) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator instance = new KeyPairGenerator();
        instance.algorithm = algorithm;
        java.security.KeyPairGenerator keyGen = java.security.KeyPairGenerator.getInstance(algorithm, "BC");
        keyGen.initialize(keyLen);
        instance.keyPair = keyGen.generateKeyPair();
        instance.privateKey = instance.getKeyPair().getPrivate();
        instance.publicKey = instance.getKeyPair().getPublic();
        return instance;
    }
    
    public static KeyPairGenerator getInstance(String algorithm) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator instance = new KeyPairGenerator();
        instance.algorithm = algorithm;
        java.security.KeyPairGenerator keyGen = java.security.KeyPairGenerator.getInstance(algorithm, "BC");
        keyGen.initialize(4096);
        instance.keyPair = keyGen.generateKeyPair();
        instance.privateKey = instance.getKeyPair().getPrivate();
        instance.publicKey = instance.getKeyPair().getPublic();
        return instance;
    }
    
    public static KeyPairGenerator getInstance(String algorithm, ECGenParameterSpec ecSpec) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyPairGenerator instance = new KeyPairGenerator();
        instance.algorithm = algorithm;
        java.security.KeyPairGenerator keyGen = java.security.KeyPairGenerator.getInstance(algorithm, "BC");
        keyGen.initialize(ecSpec, new SecureRandom());
        instance.keyPair = keyGen.generateKeyPair();
        instance.privateKey = instance.getKeyPair().getPrivate();
        instance.publicKey = instance.getKeyPair().getPublic();
        return instance;
    }

    public void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(key);
            fos.flush();
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    public boolean genFiles() {
        try{
            genFiles(null, null);
            return true;
        } catch(IOException | NoSuchAlgorithmException ex){
            LOG.error("genFiles failed.", ex);
        }
        return false;
    }
    
    /**
     * 
     * @param pubPath
     * @param priPath
     * @throws NoSuchAlgorithmException
     * @throws IOException 
     */
    public void genFiles(String pubPath, String priPath) throws NoSuchAlgorithmException, IOException{
        if(pubPath == null)
            writeToFile(getAlgorithm()+"/publicKey", Base64.getEncoder().encode(getPublicKey().getEncoded()));
        else 
            writeToFile(pubPath, Base64.getEncoder().encode(getPublicKey().getEncoded()));
        if(priPath == null)
            writeToFile(getAlgorithm()+"/privateKey", Base64.getEncoder().encode(getPrivateKey().getEncoded()));
        else
            writeToFile(priPath, Base64.getEncoder().encode(getPrivateKey().getEncoded()));
    }

    /**
     * @return the keyPair
     */
    public KeyPair getKeyPair() {
        return keyPair;
    }

    /**
     * @return the algorithm
     */
    public String getAlgorithm() {
        return algorithm;
    }
    
}
