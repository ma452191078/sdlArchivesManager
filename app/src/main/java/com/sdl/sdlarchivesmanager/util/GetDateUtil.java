package com.sdl.sdlarchivesmanager.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by majingyuan on 15/12/21.
 * 日期格式化
 */
public class GetDateUtil {
    public GetDateUtil() {

    }

    public int getDateDayCount(Date start, Date end) {
        int diff;
        diff = (int) (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
        return diff;
    }

    public String getDateDay_Seconds(Date date) {
        String day;
        SimpleDateFormat fromatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        day = fromatter.format(date);
        return day;
    }

    public String getDateDay_Day(Date date) {
        String day;
        SimpleDateFormat fromatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        day = fromatter.format(date);
        return day;
    }

    public Date getDate(long date){

        Date day = new Date(date - ((long) (Math.random() * 1000 * 60 * 60 * 24 * 365)));

        return day;
    }
}
