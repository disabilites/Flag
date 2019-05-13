package com.springboot.flag.utils;

import java.util.Calendar;
import java.util.Date;

public class isSameDate {

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))
                && (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH))
                && (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH));
    }
    public static boolean isSameMinute(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return (calendar1.get(Calendar.HOUR) == calendar2.get(Calendar.HOUR))
                && (calendar1.get(Calendar.MINUTE) == calendar2.get(Calendar.MINUTE));
    }
}
