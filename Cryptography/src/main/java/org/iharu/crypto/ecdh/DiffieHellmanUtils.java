/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto.ecdh;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.security.interfaces.ECPublicKey;

import javax.crypto.KeyAgreement;

import java.util.*;
//import java.io.Console;
import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.ECFieldFp;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

//import static javax.xml.bind.DatatypeConverter.printHexBinary;
//import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.iharu.crypto.aes.AesUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 * https://www.cnblogs.com/xinzhao/p/8963724.html
 * http://www.bouncycastle.org/wiki/pages/viewpage.action?pageId=362269
 * https://neilmadden.blog/2016/05/20/ephemeral-elliptic-curve-diffie-hellman-key-agreement-in-java/
 * https://studygolang.com/articles/22761
 * https://security.stackexchange.com/questions/129910/ecdsa-why-do-ssh-keygen-and-java-generated-public-keys-have-different-sizes
 * https://golang.org/src/crypto/elliptic/p256.go
 */
public class DiffieHellmanUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DiffieHellmanUtils.class);
    
    // ANSI X9.62 standard defines a 65-byte format for representing such X and Y; 
    // basically, a first byte of value 0x04, followed by X over exactly 32 bytes (in big-endian notation), 
    // then Y over another 32 bytes
    // a point in X9.62 format is either 67 octets (Java bytes) if compressed
    // or 133 octets if uncompressed, never any other length
    private static final String DEFAULT_CURVE = "prime256v1";
    
    //NIST P-256 curve, which has been specified to work in a 256-bit field, 
        //i.e. X and Y are two 256-bit integers
//    private static final String DEFAULT_CURVE = "P-256";
    private static final byte UNCOMPRESSED_POINT_INDICATOR = 0x04;
    
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    public static KeyPair GenKeyPair(){
        return GenKeyPair(DEFAULT_CURVE);
    }
    
    public static KeyPair GenKeyPair(String curve){
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDH", "BC");
            keyGen.initialize(ECNamedCurveTable.getParameterSpec(DEFAULT_CURVE));
            KeyPair pair = keyGen.generateKeyPair();
            return pair;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException ex) {
            Logger.getLogger(DiffieHellmanUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static KeyPair GenGoKeyPair(){
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDH", "BC");
            keyGen.initialize(GoP256Curve());
//            keyGen.initialize(256, new SecureRandom());
            KeyPair pair = keyGen.generateKeyPair();
            return pair;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | IOException ex) {
            Logger.getLogger(DiffieHellmanUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static ECPublicKey DecodeGoECPublicKey(final byte[] pubkey){
        try {
            return DecodeGoECPublicKey(pubkey, GoP256Curve());
        } catch (IOException ex) {
            Logger.getLogger(DiffieHellmanUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * return 64 bits which is not ANSI X9.62 specification
     * @param pubKey
     * @return 
     */
    public static byte[] EncodeECPublicKey(ECPublicKey pubKey){
        return encodeECPublicKey(pubKey);
    }
    
    /**
     * return 65 bits which matching ANSI X9.62 specification
     * @param pubKey
     * @return 
     */
    public static byte[] EncodeGoECPublicKey(ECPublicKey pubKey){
        return toUncompressedPoint(pubKey);
    }
    
    public static ECPublicKey DecodeGoECPublicKey(final byte[] pubkey, ECParameterSpec params){
        try {
            return fromUncompressedPoint(pubkey, params);
        } catch (Exception ex) {
            Logger.getLogger(DiffieHellmanUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static ECPublicKey DecodeECPublicKey(final byte[] pubkey){
        return DecodeECPublicKey(pubkey, ECNamedCurveTable.getParameterSpec(DEFAULT_CURVE));
    }
    
    public static ECPublicKey DecodeECPublicKey(final byte[] pubkey, org.bouncycastle.jce.spec.ECParameterSpec params) {
        if(pubkey == null  || params == null){
            return null;
        }
        try {
            int offset = 0;
            if (pubkey[0] == UNCOMPRESSED_POINT_INDICATOR) {
                offset++;
            }

//            int keySizeBytes = (params.getN().bitLength() + 7) >> 3;
            int keySizeBytes = (params.getN().bitLength() + Byte.SIZE - offset)
                    / Byte.SIZE;
            if (pubkey.length != offset + 2 * keySizeBytes) {
                throw new IllegalArgumentException(
                        "Invalid uncompressedPoint encoding, not the correct size");
            }
            
            BigInteger x = new BigInteger(1, Arrays.copyOfRange(pubkey, offset,
                    offset + keySizeBytes));
            offset += keySizeBytes;
            BigInteger y = new BigInteger(1, Arrays.copyOfRange(pubkey, offset,
                    offset + keySizeBytes));
            ECParameterSpec spec = new ECNamedCurveSpec(DEFAULT_CURVE, params.getCurve(), params.getG(), params.getN(), params.getH(), params.getSeed());
            KeyFactory eckf = KeyFactory.getInstance("EC");
            return (ECPublicKey) eckf.generatePublic(new ECPublicKeySpec(new ECPoint(x, y), spec));
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(DiffieHellmanUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DiffieHellmanUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static byte[] toUncompressedPoint(final ECPublicKey publicKey) {

        int keySizeBytes = (publicKey.getParams().getOrder().bitLength() + Byte.SIZE - 1)
                / Byte.SIZE;
        final byte[] uncompressedPoint = new byte[1 + 2 * keySizeBytes];
        int offset = 0;
        uncompressedPoint[offset++] = 0x04;

        final byte[] x = publicKey.getW().getAffineX().toByteArray();
        if (x.length <= keySizeBytes) {
            System.arraycopy(x, 0, uncompressedPoint, offset + keySizeBytes
                    - x.length, x.length);
        } else if (x.length == keySizeBytes + 1 && x[0] == 0) {
            System.arraycopy(x, 1, uncompressedPoint, offset, keySizeBytes);
        } else {
            throw new IllegalStateException("x value is too large");
        }
        offset += keySizeBytes;

        final byte[] y = publicKey.getW().getAffineY().toByteArray();
        if (y.length <= keySizeBytes) {
            System.arraycopy(y, 0, uncompressedPoint, offset + keySizeBytes
                    - y.length, y.length);
        } else if (y.length == keySizeBytes + 1 && y[0] == 0) {
            System.arraycopy(y, 1, uncompressedPoint, offset, keySizeBytes);
        } else {
            throw new IllegalStateException("y value is too large");
        }

        return uncompressedPoint;
    }
    
    private static ECPublicKey fromUncompressedPoint(final byte[] uncompressedPoint, final ECParameterSpec params)
            throws Exception {
        int offset = 0;
        if (uncompressedPoint[offset++] != UNCOMPRESSED_POINT_INDICATOR) {
            throw new IllegalArgumentException(
                    "Invalid uncompressedPoint encoding, no uncompressed point indicator");
        }
//        int byteLen = (params.getOrder().bitLength() + 7) >> 3;
        int keySizeBytes = (params.getOrder().bitLength() + Byte.SIZE - 1)
                / Byte.SIZE;
        if (uncompressedPoint.length != 1 + 2 * keySizeBytes) {
            throw new IllegalArgumentException(
                    "Invalid uncompressedPoint encoding, not the correct size");
        }

        final BigInteger x = new BigInteger(1, Arrays.copyOfRange(
                uncompressedPoint, offset, offset + keySizeBytes));
        offset += keySizeBytes;
        final BigInteger y = new BigInteger(1, Arrays.copyOfRange(
                uncompressedPoint, offset, offset + keySizeBytes));
//        System.out.println(params.getOrder().toString(16)); // N
//        System.out.println(params.getCurve().getA().toString(16)); // A
//        System.out.println(params.getCurve().getB().toString(16)); // B
//        System.out.println(params.getGenerator().getAffineX().toString(16)); // X
//        System.out.println(params.getGenerator().getAffineY().toString(16)); // Y
        ECPoint w = new ECPoint(x, y);
//        ECParameterSpec ecp = new ECParameterSpec(
//            GoP256Curve().getCurve(),
//            GoP256Curve().getGenerator(),
//            GoP256Curve().getOrder(),
//            GoP256Curve().getCofactor());
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(w, params);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return (ECPublicKey) keyFactory.generatePublic(ecPublicKeySpec);
    }
    
    public static ECPublicKey GenPubKey(java.security.interfaces.ECPrivateKey privateKey){
        try{
            // Generate public key from private key
            ECPoint apoint = privateKey.getParams().getGenerator();
            BigInteger x = apoint.getAffineX(), y = apoint.getAffineY();
            // construct point plus params to pubkey
            ECPoint bpoint = new ECPoint (x,y); 
            ECPublicKeySpec bpubs = new ECPublicKeySpec (bpoint, privateKey.getParams());
            KeyFactory kfa = KeyFactory.getInstance ("EC");
            return (ECPublicKey) kfa.generatePublic(bpubs);
        } catch(Exception ex){
            LOG.info("", ex);
        }
        return null;
    }
    
    public static org.bouncycastle.jce.interfaces.ECPublicKey GenBCPubKey(ECPrivateKey privateKey){
        try{
            // Generate public key from private key
            org.bouncycastle.jce.spec.ECParameterSpec ecSpec = privateKey.getParameters();

            org.bouncycastle.math.ec.ECPoint Q = ecSpec.getG().multiply(privateKey.getD());
            byte[] publicDerBytes = Q.getEncoded(false);
            org.bouncycastle.math.ec.ECPoint point = ecSpec.getCurve().decodePoint(publicDerBytes);
            org.bouncycastle.jce.spec.ECPublicKeySpec pubSpec = new org.bouncycastle.jce.spec.ECPublicKeySpec(point, ecSpec);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return  (org.bouncycastle.jce.interfaces.ECPublicKey) keyFactory.generatePublic(pubSpec);
        } catch(Exception ex){
            
        }
        return null;
    }
    
    public static ECParameterSpec GoP256Curve() throws IOException {
	// 素数P
	BigInteger p = new BigInteger("ffffffff00000001000000000000000000000000ffffffffffffffffffffffff", 16);

	// 基于素数P的有限域
	ECFieldFp gfp = new ECFieldFp(p);
//        HexEncoder hexEncoder = new HexEncoder();
	// 在有限域上的椭圆曲线y2 = x3 + ax + b
	EllipticCurve ellipticCurve = new EllipticCurve(gfp,
//			new BigInteger(1, hexEncoder.decodeStrict("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC")),
                new BigInteger("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC", 16),
			new BigInteger("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b", 16));

	// 基点G
	ECPoint G = new ECPoint(new BigInteger("6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296", 16),
			new BigInteger("4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5", 16));

	// G的阶
	BigInteger n = new BigInteger("ffffffff00000000ffffffffffffffffbce6faada7179e84f3b9cac2fc632551", 16);

	// 设置基点
	ECParameterSpec ecParameterSpec = new ECParameterSpec(ellipticCurve, G, n, 1);
	return ecParameterSpec;
}
    
    
    private static byte[] encodeECPublicKey(ECPublicKey pubKey) {
        int keyLengthBytes = pubKey.getParams().getOrder().bitLength()
                / Byte.SIZE;
        byte[] publicKeyEncoded = new byte[2 * keyLengthBytes];

        int offset = 0;

        BigInteger x = pubKey.getW().getAffineX();
        byte[] xba = x.toByteArray();
        if (xba.length > keyLengthBytes + 1 || xba.length == keyLengthBytes + 1
                && xba[0] != 0) {
            throw new IllegalStateException(
                    "X coordinate of EC public key has wrong size");
        }

        if (xba.length == keyLengthBytes + 1) {
            System.arraycopy(xba, 1, publicKeyEncoded, offset, keyLengthBytes);
        } else {
            System.arraycopy(xba, 0, publicKeyEncoded, offset + keyLengthBytes
                    - xba.length, xba.length);
        }
        offset += keyLengthBytes;

        BigInteger y = pubKey.getW().getAffineY();
        byte[] yba = y.toByteArray();
        if (yba.length > keyLengthBytes + 1 || yba.length == keyLengthBytes + 1
                && yba[0] != 0) {
            throw new IllegalStateException(
                    "Y coordinate of EC public key has wrong size");
        }

        if (yba.length == keyLengthBytes + 1) {
            System.arraycopy(yba, 1, publicKeyEncoded, offset, keyLengthBytes);
        } else {
            System.arraycopy(yba, 0, publicKeyEncoded, offset + keyLengthBytes
                    - yba.length, yba.length);
        }

        return publicKeyEncoded;
    }
    
    public static SecretKey GenSecretKey(byte[] pubKey, ECPrivateKey privKey) {
        return AesUtils.ParseSecretKey(GenSecretKey(DecodeECPublicKey(pubKey), privKey));
    }
    
    public static byte[] GenSecretKey(ECPublicKey pubKey, ECPrivateKey privKey) {
        if(pubKey == null || privKey == null)
            return null;
        try {
            // Perform key agreement
            KeyAgreement ka = KeyAgreement.getInstance("ECDH");
            ka.init(privKey);
            // if this key exchange is with one other party, 
            // doPhase needs to be called once, with the lastPhase flag set to true. 
            // If this key exchange is with two other parties, doPhase needs to be called twice, 
            // the first time setting the lastPhase flag to false, and the second time setting it to true. 
            // There may be any number of parties involved in a key exchange.
            ka.doPhase(pubKey, true);
            
            // Read shared secret
            return ka.generateSecret();
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            Logger.getLogger(DiffieHellmanUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//    public static void test() throws Exception {
//        Console console = System.console();
//        // Generate ephemeral ECDH keypair
//        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
//        kpg.initialize(256);
//        KeyPair kp = kpg.generateKeyPair();
//        byte[] ourPk = kp.getPublic().getEncoded();
//
//        // Display our public key
//        console.printf("Public Key: %s%n", printHexBinary(ourPk));
//
//        // Read other's public key:
//        byte[] otherPk = parseHexBinary(console.readLine("Other PK: "));
//
//        KeyFactory kf = KeyFactory.getInstance("EC");
//        X509EncodedKeySpec pkSpec = new X509EncodedKeySpec(otherPk);
//        PublicKey otherPublicKey = kf.generatePublic(pkSpec);
//
//        // Perform key agreement
//        KeyAgreement ka = KeyAgreement.getInstance("ECDH");
//        ka.init(kp.getPrivate());
//        ka.doPhase(otherPublicKey, true);
//
//        // Read shared secret
//        byte[] sharedSecret = ka.generateSecret();
//        console.printf("Shared secret: %s%n", printHexBinary(sharedSecret));
//
//        // Derive a key from the shared secret and both public keys
//        MessageDigest hash = MessageDigest.getInstance("SHA-256");
//        hash.update(sharedSecret);
//        // Simple deterministic ordering
//        List<ByteBuffer> keys = Arrays.asList(ByteBuffer.wrap(ourPk), ByteBuffer.wrap(otherPk));
//        Collections.sort(keys);
//        hash.update(keys.get(0));
//        hash.update(keys.get(1));
//
//        byte[] derivedKey = hash.digest();
//        console.printf("Final key: %s%n", printHexBinary(derivedKey));
//      }
    
    public static void fnc() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeySpecException, InvalidKeyException{
        Security.addProvider(new BouncyCastleProvider());

        // Alice sets up the exchange
        KeyPairGenerator aliceKeyGen = KeyPairGenerator.getInstance("ECDH", "BC");
        aliceKeyGen.initialize(new ECGenParameterSpec("prime256v1"), new SecureRandom());

        KeyPair alicePair = aliceKeyGen.generateKeyPair();
        ECPublicKey alicePub = (ECPublicKey)alicePair.getPublic();
        ECPrivateKey alicePvt = (ECPrivateKey)alicePair.getPrivate();

        byte[] alicePubEncoded = alicePub.getEncoded();
        byte[] alicePvtEncoded = alicePvt.getEncoded();

//        System.out.println("Alice public: " + DatatypeConverter.printHexBinary(alicePubEncoded));
//        System.out.println("Alice private: " + DatatypeConverter.printHexBinary(alicePvtEncoded));


        // POST hex(alicePubEncoded)

        // Bob receives Alice's public key

        KeyFactory kf = KeyFactory.getInstance("EC");
        PublicKey remoteAlicePub = kf.generatePublic(new X509EncodedKeySpec(alicePubEncoded));

        KeyPairGenerator bobKeyGen = KeyPairGenerator.getInstance("ECDH", "BC");
        bobKeyGen.initialize(new ECGenParameterSpec("prime256v1"), new SecureRandom());

        KeyPair bobPair = bobKeyGen.generateKeyPair();
        ECPublicKey bobPub = (ECPublicKey)bobPair.getPublic();
        ECPrivateKey bobPvt = (ECPrivateKey)bobPair.getPrivate();

        byte[] bobPubEncoded = bobPub.getEncoded();
        byte[] bobPvtEncoded = bobPvt.getEncoded();

//        System.out.println("Bob public: " + DatatypeConverter.printHexBinary(bobPubEncoded));
//        System.out.println("Bob private: " + DatatypeConverter.printHexBinary(bobPvtEncoded));

        KeyAgreement bobKeyAgree = KeyAgreement.getInstance("ECDH");
        bobKeyAgree.init(bobPvt);
        bobKeyAgree.doPhase(remoteAlicePub, true);

//        System.out.println("Bob secret: " + DatatypeConverter.printHexBinary(bobKeyAgree.generateSecret()));


        // RESPOND hex(bobPubEncoded)

        // Alice derives secret

        KeyFactory aliceKf = KeyFactory.getInstance("EC");
        PublicKey remoteBobPub = aliceKf.generatePublic(new X509EncodedKeySpec(bobPubEncoded));

        KeyAgreement aliceKeyAgree = KeyAgreement.getInstance("ECDH");
        aliceKeyAgree.init(alicePvt);
        aliceKeyAgree.doPhase(remoteBobPub, true);

//        System.out.println("Alice secret: " + DatatypeConverter.printHexBinary(aliceKeyAgree.generateSecret()));
    }
    
    
    private static ECPublicKey decodeECPublicKey(ECParameterSpec params, final byte[] pubkey) {
        try {
            int keySizeBytes = params.getOrder().bitLength() / Byte.SIZE;
//            System.out.println("decodeECPublicKey - keySizeBytes: " + keySizeBytes);
//            ECPoint apoint = params.getGenerator();
//            BigInteger x = apoint.getAffineX(), y = apoint.getAffineY();
//            // construct point plus params to pubkey
//            ECPoint bpoint = new ECPoint (x,y); 
//            ECPublicKeySpec bpubs = new ECPublicKeySpec (bpoint, params);
//            KeyFactory kfa = KeyFactory.getInstance ("EC");
//            return (ECPublicKey) kfa.generatePublic(bpubs);
            
            
            
            int offset = 0;
            BigInteger x = new BigInteger(1, Arrays.copyOfRange(pubkey, offset,
                    offset + keySizeBytes));
            offset += keySizeBytes;
            BigInteger y = new BigInteger(1, Arrays.copyOfRange(pubkey, offset,
                    offset + keySizeBytes));
            ECPoint point = new ECPoint(x, y);
            KeyFactory eckf = KeyFactory.getInstance("EC");
            return (ECPublicKey) eckf.generatePublic(new ECPublicKeySpec(point, params));
        } catch (InvalidKeySpecException ex) {
            ExceptionUtils.getStackTrace(ex);
            Logger.getLogger(DiffieHellmanUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DiffieHellmanUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
