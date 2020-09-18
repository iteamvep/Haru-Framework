/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class ScriptUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ScriptUtils.class);
    
	private final static String ENGINE_NAME = "Nashorn";
//        private final static String ENGINE_NAME = "JavaScript";
        private ScriptEngine engine;
        
        public boolean initScriptEngine(Object scriptObject, Map<String, Object> factors) throws ScriptException, FileNotFoundException{
            //1.创建脚本引擎
            engine = createScriptEngine();
            //2.绑定全局变量
            bindingContextVariable(getEngine(), factors);
            //3.读取脚本内容
            return readScriptIntoEngine(getEngine(), scriptObject);
        }
        
        public boolean initScriptEngine(Object scriptObject, Map<String, Object> factors, List<String> addition) throws ScriptException, FileNotFoundException{
            //1.创建脚本引擎
            engine = createScriptEngine();
            //2.绑定全局变量
            bindingContextVariable(getEngine(), factors);
            patchInitData(getEngine(), addition);
            //3.读取脚本内容
            return readScriptIntoEngine(getEngine(), scriptObject);
        }
 
	public Object runScriptFunction(String functionName, Object... args) throws ScriptException, NoSuchMethodException{
		
		//4.调用脚本方法，返回结果
		Object result = invokeTargetMethod(getEngine(), functionName, args);
//		System.out.println(result);
                return result;
	}
        
        public Object getScriptProperty(String property) throws ScriptException, NoSuchMethodException {
                Object result = getEngine().get(property);
//		System.out.println(result);
                return result;
	}
 
	/**
	 * @param engine
	 * @param var1
	 * @param var2
	 * @throws ScriptException
	 * @throws NoSuchMethodException
	 */
	private Object invokeTargetMethod(ScriptEngine engine, String functionName, Object... args) throws NoSuchMethodException,
			ScriptException {
		Object result = null;
		if (engine instanceof Invocable) {
			Invocable io = (Invocable) engine;
			result =  io.invokeFunction(functionName, args);
		}
		return result;
	}
 
	private boolean readScriptIntoEngine(ScriptEngine engine, Object scriptObject) throws ScriptException, FileNotFoundException {
            InputStream resourceAsStream = null;
            Reader reader = null;
            if(scriptObject instanceof String){
                String scriptPath = (String) scriptObject;
                HttpURLConnection conn = null;
                if(scriptPath.startsWith("http")){
//                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
                    try { 
                        conn = (HttpURLConnection) new URL(scriptPath).openConnection();
                        conn.setConnectTimeout(3*1000); 
                        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36"); 
                        conn.setRequestProperty("DNT", "1");
                        conn.setRequestProperty("Cache-Control", "no-cache, no-store, max-age=0, s-maxage=0, must-revalidate, proxy-revalidate");
                        conn.setRequestProperty("Prama", "no-cache");
                        byte[] buffer = new byte[1024];  
                        int len = 0;  
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
                        while((len = conn.getInputStream().read(buffer)) != -1) {  
                            bos.write(buffer, 0, len);  
                        }  
                        bos.close();  
                        reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new ByteArrayInputStream(bos.toByteArray()))));
                    } catch (IOException ex) {
                        LOG.error("获取脚本文件失败， 文件地址为：{}", scriptPath);
                    } finally {
                        if(conn != null)
                            conn.disconnect();
                    }
                } else if(resourceAsStream == null) {
                    resourceAsStream = new FileInputStream(scriptPath);
                }
            } else if (scriptObject instanceof File) {
                resourceAsStream = new FileInputStream((File) scriptObject);
            }
            if(reader == null && resourceAsStream == null)
                return false;
            if(reader == null)
                reader = new InputStreamReader(resourceAsStream);
            engine.eval(reader);
            return true;
	}
 
	private void bindingContextVariable(ScriptEngine engine, Map<String, Object> factors) {
            if(factors == null)
                return;
            Bindings binding = engine.createBindings();
            factors.forEach((k, v) -> {
                binding.put(k, v);
            });
            engine.setBindings(binding, ScriptContext.ENGINE_SCOPE);
	}
        
        private void patchInitData(ScriptEngine engine, List<String> addition) throws ScriptException {
            if(addition == null)
                return;
            for(String item:addition){
                engine.eval(item);
            }
	}
 
	private static ScriptEngine createScriptEngine() {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName(ENGINE_NAME);
		return engine;
	}

    /**
     * @return the engine
     */
    public ScriptEngine getEngine() {
        return engine;
    }
}
