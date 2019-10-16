/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto.argon2;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.bouncycastle.util.Strings;
import org.iharu.crypto.util.BytesUtils;
import org.iharu.util.StringUtils;

/**
 *
 * @author iHaru
 */
public class Argon2Utils {
    private static final int DEFAULT_OUTPUTLEN = 40;
    private static final int ITERATIONS = 16;
    private static final int MEMORY = 4;
    private static final int PARALLELISM = 2;
    private static final int version = Argon2Parameters.ARGON2_VERSION_13;
    
    public static byte[] HashPasswordWithSalt(String password, String salt){
        return hash(version, ITERATIONS, MEMORY, PARALLELISM, password, salt, DEFAULT_OUTPUTLEN);
    }
    
    private static byte[] hash(int version, int iterations, int memory, int parallelism,
                          String password, String salt, int outputLength)
    {
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_i)
            .withVersion(version)
            .withIterations(iterations)
            .withMemoryPowOfTwo(memory)
            .withParallelism(parallelism)
            .withSalt(BytesUtils.StringToUnicodeByteArray(salt));

        //
        // Set the password.
        //
        Argon2BytesGenerator gen = new Argon2BytesGenerator();

        gen.init(builder.build());

        byte[] result = new byte[outputLength];
        gen.generateBytes(BytesUtils.StringToUnicodeByteArray(password), result, 0, result.length);
        return result;
    }
}
