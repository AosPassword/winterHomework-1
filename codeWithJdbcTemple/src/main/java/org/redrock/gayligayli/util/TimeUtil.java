package org.redrock.gayligayli.util;

import java.util.Date;

public class TimeUtil {
    public static boolean requestIsNotOvertime(String timestamp, long time) {
        long nowTime = (long) Math.ceil(new Date().getTime() / 1000);
        long requestTime = Long.parseLong(timestamp);
        return Math.abs(nowTime - requestTime) <= time ;
    }
}
