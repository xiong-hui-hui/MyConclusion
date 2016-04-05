package Utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hui.xiong on 2016/3/2.
 */
public class DateUtils {
    /**
     *获取今天的日期，月份，年份
     */
    public static int getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentMonth(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取year，month这个月的第一天是星期几
     * @param year
     * @param month
     * @return week position
     */
    public static int getDayOfWeekInMonth(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DATE, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取year，month这个月的天数
     * @param year
     * @param month
     * @return week position
     */
    public static int getDayCountOfMonth(int year, int month){
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29; // 闰年2月29天
        }
        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }
        return days;
    }

/*    public static SSMonth prevMonth(int year, int month){
        if(month == 1){
            year -= 1;
            month = 12;
        }else{
            month -= 1;
        }
        return new SSMonth(year, month);
    }

    public static SSMonth nextMonth(int year, int month){
        if(month == 12){
            year += 1;
            month = 1;
        }else{
            month += 1;
        }
        return new SSMonth(year, month);
    }*/

    public static boolean isToday(int year, int month, int day) {
        return year == getCurrentYear() && month == getCurrentMonth() && day == getCurrentDay();
    }

    public static int countDays(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        Calendar startC = Calendar.getInstance();
        startC.set(Calendar.YEAR, startYear);
        startC.set(Calendar.MONTH, startMonth);
        startC.set(Calendar.DAY_OF_MONTH, startDay);
        Calendar endC = Calendar.getInstance();
        endC.set(Calendar.YEAR, endYear);
        endC.set(Calendar.MONTH, endMonth);
        endC.set(Calendar.DAY_OF_MONTH, endDay);
        return (int) ((endC.getTimeInMillis() - startC.getTimeInMillis()) / 86400000 + 1);
    }

    public static Date getDateFromString(int year, int month) {
        return getDateFromString(year,month,1);
    }

    public static Date getDateFromString(int year, int month,int day) {
        String dateString = year + "-" + (month > 9 ? month : ("0" + month))
                + "-"+(day > 9 ? day : ("0" + day));
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param str 默认格式为yyyy-MM-dd
     * @return
     */
    public static Date getDateFromString(String str){
        return getDateFromString(str,"yyyy-MM-dd");
    }
    public static Date getDateFromString(String str,String format){
        Date date=null;
        SimpleDateFormat sdf =new SimpleDateFormat("format");
        try {
            date =sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getStrFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(date);
    }

    public static String getStrFromDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(date);
    }

    public static String getStrFromDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(c.getTime());
    }

    /**比较日期
     * @param date1
     * @param date2
     * @param format
     * @return -1:date1<date2, 0, 1:date1>date2, -2:异常
     */
    public static int compareDate(String date1,String date2,String format){
        SimpleDateFormat sdf =new SimpleDateFormat(format);
        try {
            Date dt1= sdf.parse(date1);
            Date dt2= sdf.parse(date2);
            if (dt1.before(dt2)){
                return -1;
            }else if(dt1.after(dt2)){
                return 1;
            }else {
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return -2;
        }
    }
    public static int compareDate(String date1,String date2){
        return compareDate(date1,date2,"yyyy-MM-dd");
    }

    /**
     * 获取当前天数指定的前N天或后N天
     *
     * @param paramDay  传入的某一天
     * @param offsetDay 某一天的后N天或前N天
     * @return
     */
    public static String getDefineDate(String paramDay, int offsetDay) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDay = sdf.parse(paramDay);
            Calendar calendar = Calendar.getInstance(); // 得到日历
            calendar.setTime(currentDay); // 把当前时间赋值给日历
            calendar.add(Calendar.DAY_OF_MONTH, offsetDay); //
            String defineDay = sdf.format(calendar.getTime()); // 格式化当前时间
            return defineDay;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 根据生日获取年龄
     * @param birthday 格式为yyyy-MM-dd
     * @return 如果为-1则传入非法参数
     */
    public static int getAge(String birthday) {
        try {
            if (TextUtils.isEmpty(birthday))
                return 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date birthdayDate = sdf.parse(birthday);
            String currTimeStr = sdf.format(new Date());
            Date currDate = sdf.parse(currTimeStr);
            if (birthdayDate.getTime() > currDate.getTime()) {
                 return -1;
            }
            long age = (currDate.getTime() - birthdayDate.getTime())
                    / (24 * 60 * 60 * 1000) + 1;
            String year = new DecimalFormat("0.00").format(age / 365f);
            if (TextUtils.isEmpty(year))
                return 0;
            return new Double(year).intValue() + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int[] getWeekSunday(int year, int month, int day, int pervious) {
        int[] time = new int[3];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DAY_OF_MONTH, pervious);
        time[0] = c.get(Calendar.YEAR);
        time[1] = c.get(Calendar.MONTH) + 1;
        time[2] = c.get(Calendar.DAY_OF_MONTH);
        return time;
    }


}
