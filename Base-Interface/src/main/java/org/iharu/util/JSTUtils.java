/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author iHaru
 */
public class JSTUtils {
    
    public static Calendar getJSTCalendar() {
        // 1、取得本地时间：
        java.util.Calendar theCalendar = java.util.Calendar.getInstance();
        
        // 2、取得时间偏移量：
        int zoneOffset = theCalendar.get(java.util.Calendar.ZONE_OFFSET);

        // 3、取得夏令时差：
        int dstOffset = theCalendar.get(java.util.Calendar.DST_OFFSET);
    ;
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        theCalendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        
        //5、加上日本时区的时差时间，即获得实际JST时间
        theCalendar.add(java.util.Calendar.MILLISECOND, TimeZone.getTimeZone("JST").getRawOffset());
        
        return theCalendar;
    }
    
}
