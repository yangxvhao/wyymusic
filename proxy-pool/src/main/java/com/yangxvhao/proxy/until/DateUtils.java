package com.yangxvhao.proxy.until;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yangxvhao
 * @date 18-1-2.
 */

public class DateUtils {

    /**
     * 需要的格式
     * @return yyyymmdd
     */
    public static String getTimeFormat(String pattern){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String getTime(){
        return getTimeFormat("yyyyMMdd");
    }
}
