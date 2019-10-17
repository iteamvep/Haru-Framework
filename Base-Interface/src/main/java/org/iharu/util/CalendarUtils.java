/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import static org.iharu.constant.ConstantValue.TIMEZONEID;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class CalendarUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CalendarUtils.class);
    
    public static Calendar getTimezoneCalendar(String timezone) {
        // 1、取得UTC时间：
        long utc = OffsetDateTime.now(ZoneOffset.of("+00:00")).toInstant().toEpochMilli();
        
        // 2、取得时间偏移量：
        int zoneOffset = TimeZone.getTimeZone(timezone).getRawOffset();

        // 3、取得夏令时差：
        int dstOffset = TimeZone.getTimeZone(timezone).getDSTSavings();
        
        //4、加上时区的时差时间，即获得实际当地时间
        Calendar theCalendar = Calendar.getInstance();
        theCalendar.setTimeInMillis(utc + zoneOffset + dstOffset);
        
        return theCalendar;
    }
    
    public static ZonedDateTime getZonedDateTime(String zoneId){
        return zoneId == null ? 
                ZonedDateTime.now(ZoneId.of(TIMEZONEID)) : ZonedDateTime.now(ZoneId.of(zoneId));
    }
    
    public static long getZonedTimestamp(){
        return ZonedDateTime.now(ZoneId.of(TIMEZONEID)).toInstant().toEpochMilli();
    }
    
    public static long getUTCTimestamp(){
        return ZonedDateTime.now(ZoneId.of("UTC")).toInstant().toEpochMilli();
    }
    
    public static Map<String, String> GetAllZoneIdsAndItsOffSet() {

        Map<String, String> result = new HashMap<>();

        LocalDateTime localDateTime = LocalDateTime.now();

        for (String zoneId : ZoneId.getAvailableZoneIds()) {

            ZoneId id = ZoneId.of(zoneId);

            // LocalDateTime -> ZonedDateTime
            ZonedDateTime zonedDateTime = localDateTime.atZone(id);

            // ZonedDateTime -> ZoneOffset
            ZoneOffset zoneOffset = zonedDateTime.getOffset();

            //replace Z to +00:00
            String offset = zoneOffset.getId().replaceAll("Z", "+00:00");

            result.put(id.toString(), offset);

        }

        return result;

    }
}
