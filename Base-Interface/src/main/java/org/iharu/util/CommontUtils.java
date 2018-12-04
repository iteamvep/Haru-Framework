/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author x5171
 */
public class CommontUtils {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CommontUtils.class);
    
    public static String getFileHex(String file){
        try {
            return DigestUtils.md5Hex(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        } catch (IOException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
    
    public static String getFileHex(File file){
        try {
            return DigestUtils.md5Hex(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        } catch (IOException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
    
    public static Date CvtToUTC( Date date ){
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance() ;
        cal.setTime(date);
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime();
    }
    
}
