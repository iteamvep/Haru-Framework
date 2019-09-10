/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import static org.iharu.constant.ConstantValue.FILESEPARATOR;

/**
 *
 * @author iHaru
 */
public class HttpClientUtils {
    
    public static boolean DownloadFile(String url, String folder, String filename, String host, int port) throws IOException{
        HttpURLConnection conn = null;
        try { 
            if(host != null && !host.trim().isEmpty() && port > 0){
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
                conn = (HttpURLConnection) new URL(url).openConnection(proxy);
            } else {
                conn = (HttpURLConnection) new URL(url).openConnection();
            }
            conn.setConnectTimeout(5 * 1000); 
            conn.setReadTimeout(30 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36"); 
            conn.setRequestProperty("DNT", "1");
            conn.setRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
            conn.setRequestProperty("Prama", "no-cache");
            byte[] buffer = new byte[1024];  
            int len = 0;  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            while((len = conn.getInputStream().read(buffer)) != -1) {  
                bos.write(buffer, 0, len);  
            }  
            bos.close();  
            if(!new File(folder).exists())
                new File(folder).mkdirs();
            File targetFile = new File(folder + FILESEPARATOR + filename);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(bos.toByteArray());
            outStream.flush();
            outStream.close();
            if(targetFile.exists())
                return true;
        } catch (IOException ex) {
            throw ex;
        } finally {
            if(conn != null)
                conn.disconnect();
        }
        return false;
    }
    
    public static boolean DownloadFile(String url, String folder, String filename) throws IOException{
        return DownloadFile(url, folder, filename, null, -1);
    }
    
    public static String GetBody(String url, String host, int port) throws IOException {
        OkHttpClient client;
        if(host != null && !host.trim().isEmpty() && port > 0){
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
            client = new OkHttpClient.Builder().proxy(proxy).build();
        } else {
            client = new OkHttpClient();
        }
        
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36")
                .addHeader("DNT", "1")
                .addHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0")
                .addHeader("Prama", "no-cache")
                .addHeader(url, url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful() && response.body() != null)
                return response.body().string();
        }
        return null;
    }
    
    public static String GetBody(String url) throws IOException{
        return GetBody(url, null, -1);
    }
    
    public static String GetBody(String url, String body, String host, int port) throws IOException{
        OkHttpClient client;
        if(host != null && !host.trim().isEmpty() && port > 0){
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
            client = new OkHttpClient.Builder()
                    .proxy(proxy)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10,TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
        } else {
            client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10,TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();;
        }
        
        RequestBody requestBody = FormBody.create(body, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36")
                .addHeader("DNT", "1")
                .addHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0")
                .addHeader("Prama", "no-cache")
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful() && response.body() != null)
                return response.body().string();
        }
        return null;
    }
    
    public static String GetBody(String url, String body) throws IOException{
        return GetBody(url, body, null, -1);
    }
}
