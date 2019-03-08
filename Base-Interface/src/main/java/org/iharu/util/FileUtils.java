/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import static org.iharu.util.BaseConstantValue.LINESEPARATOR;

/**
 *
 * @author iHaru
 */
public class FileUtils {
    
    public static StringBuilder readFile(File file, Charset cs) throws IOException {
        if(!file.exists())
            return null;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), cs));
        String line;   
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line).append(LINESEPARATOR);
        }  
        return sb;
    }
    
    public static StringBuilder readFile(String file) throws IOException {
        return readFile(new File(file), Charset.forName("UTF-8"));
    }
    
    public static void writeFile(File file, String content, Charset cs) throws IOException {
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, cs);
        osw.write(content);
        osw.flush();
        osw.close();
    }
    
    public static void writeFile(String file, String content) throws IOException {
        writeFile(new File(file), content, Charset.forName("UTF-8"));
    }
    
    public static void appendFile(File file, String content, Charset cs) throws IOException {
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try (OutputStreamWriter out = new OutputStreamWriter(
                new FileOutputStream(file, true), // true to append
                cs
        )) {
            out.write(content);
        }
    }
    
    public static void appendFile(String file, String content) throws IOException {
        appendFile(new File(file), content, Charset.forName("UTF-8"));
    }
    
    
    
}
