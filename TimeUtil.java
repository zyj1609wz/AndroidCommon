package com.wifi.analytics;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by marks on 2016/8/19.
 */
public class TimeUtil {

    /**
     * 获取手机系统事件
     *
     * @return
     */
    public static String getCurrentTimeLocal() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 获取零时区时间
     *
     * @return
     */
    public static String getCurrentTimeZero() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC "));
        return sdf.format(new Date());
    }

    /**
     * 获取北京时间
     * 在零时区的基础上加8个小时
     *
     * @return
     */
    public static String getCurrentTimeBeijing() {
        String timeZero = getCurrentTimeZero();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(timeZero);
            calendar.setTime(date);
            calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

}
