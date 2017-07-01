package com.bfd.test.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by BFD-483 on 2017/6/30.
 */
public class RandomDate {
    static SimpleDateFormat CeshiFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String randomDateString(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            Date dateTime = new Date(date);
            return CeshiFmt2.format(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            Date dateTime = new Date(date);
            return dateTime;
            //return CeshiFmt2.format(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static long random(long begin, long end) {
        long rtnn = begin + (long) (Math.random() * (end - begin));
        if (rtnn == begin || rtnn == end) {
            return random(begin, end);
        }
        return rtnn;
    }
}
