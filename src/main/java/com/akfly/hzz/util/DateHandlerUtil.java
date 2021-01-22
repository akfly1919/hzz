package com.akfly.hzz.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateHandlerUtil {

    /**
     * 时间格式.
     */
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 到小时分钟的日期格式.
     */
    public static final String FORMAT_DATETIME_HM = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式.
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    /**
     * 全时间格式.
     */
    public static final String FORMAT_FULLTIME = "yyMMddHHmmssSSS";
    /**
     * 全时间,日期格式.
     */
    public static final String FORMAT_FULLDATE = "yyyy-MM-dd HH:mm:ss:SSS";
    /**
     * 日期格式.
     */
    public static final String FORMAT_YEARMONTH = "yyyy-MM";
    /**
     * 纯时间格式.
     */
    public static final String FORMAT_TIME = "HH:mm:ss";

    /**
     * 年月日时分秒无分隔符*
     */
    public final static String FORMAT_TRADETIME = "yyyyMMddHHmmss";

    /**
     * 年月日无分隔符*
     */
    public final static String FORMAT_TRADEDATE = "yyyyMMdd";
    /**
     * 年月无分隔符*
     */
    public final static String FORMAT_TRADED_YEARMONTH = "yyyyMM";

    /**
     * ISODateTime  yyyymmddhhmmss*
     */
    public final static String FORMAT_ISODATETIME = "yyyyMMddHHmmss";

    /**
     * ISODate  yyyymmdd*
     */
    public final static String FORMAT_ISODATE = "yyyyMMdd";

    /**
     * ISOTime  hhmmss*
     */
    public final static String FORMAT_ISOTIME = "yyyyMMdd";

//    public static final String FORMAT_DATE_HOUR = "yyyy-MM-dd.HH";
    public static final String FORMAT_DATE_HOUR = "yyyy-MM-dd HH";

    /**
     * 纯时间格式.
     */
    public static final String FORMAT_HOUR_MINUTE_TIME = "HH:mm";


    public static Date getMinuteBefore(int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());//设置当前日期
        calendar.add(Calendar.MINUTE, -min);//﹣时间查询当前时间之前
        return calendar.getTime();
    }

    public static Date getMinuteBefore(Date date, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//设置当前日期
        calendar.add(Calendar.MINUTE, -min);//﹣时间查询当前时间之前
        return calendar.getTime();
    }

    /**
     * 当前时间往前推固定毫秒数
     *
     * @param date
     * @param mills
     * @return
     */
    public static Date getMillsBefore(Date date, int mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//设置当前日期
        calendar.add(Calendar.MILLISECOND, -mills);//﹣时间查询当前时间之前
        return calendar.getTime();
    }

    /**
     * compare two kinds String with format : 12:00 , 08:00; or 12:00:00,
     * 08:00:00.<br>
     * <br>
     *
     * @param firstTime  the first time string.
     * @param secondTime the second time string.
     * @return 0 -- same 1 -- first bigger than second -1 -- first smaller than
     * second -2 -- invalid time format
     */
    public static int compareOnlyByTime(String firstTime, String secondTime) {
        try {
            String timeDelm = ":";

            // calculate the first time to integer
            int pos = firstTime.indexOf(timeDelm);
            int iFirst = Integer.parseInt(firstTime.substring(0, pos)) * 10000;
            firstTime = firstTime.substring(pos + 1);
            pos = firstTime.indexOf(timeDelm);

            if (pos > 0) {
                iFirst = iFirst + (Integer.parseInt(firstTime.substring(0, pos)) * 100)
                        + Integer.parseInt(firstTime.substring(pos + 1));
            } else {
                iFirst = iFirst + (Integer.parseInt(firstTime) * 100);
            }

            // calculate the second time string to integer
            pos = secondTime.indexOf(timeDelm);
            int iSecond = Integer.parseInt(secondTime.substring(0, pos)) * 10000;
            secondTime = secondTime.substring(pos + 1);
            pos = secondTime.indexOf(timeDelm);

            if (pos > 0) {
                iSecond = iSecond + (Integer.parseInt(secondTime.substring(0, pos)) * 100)
                        + Integer.parseInt(secondTime.substring(pos + 1));
            } else {
                iSecond = iSecond + (Integer.parseInt(secondTime) * 100);
            }

            // compare two
            if (iFirst == iSecond) {
                return 0;
            } else if (iFirst > iSecond) {
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -2;
        }
    }

    /**
     * 根据规定格式的字符串得到Calendar.<br>
     * <br>
     *
     * @param dateString 日期串.
     * @return 对应Calendar
     */
    public static Calendar getCalendar(String dateString) {
        Calendar calendar = Calendar.getInstance();
        String[] items = dateString.split("-");
        calendar
                .set(Integer.parseInt(items[0]), Integer.parseInt(items[1]) - 1, Integer.parseInt(items[2]));
        return calendar;
    }

    /**
     * 得到与当前日期相差指定天数的日期字符串.<br>
     * <br>
     *
     * @param days 前后的天数，正值为后， 负值为前.
     * @return 日期字符串
     */
    public static String getCertainDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 得到与指定日期相差指定天数的日期字符串.<br>
     * <br>
     *
     * @param dateString 指定的日期.
     * @param days       前后的天数，正值为后， 负值为前.
     * @return 日期字符串
     */
    public static String getCertainDate(String dateString, int days) {
        Calendar calendar = getCalendar(dateString);
        calendar.add(Calendar.DATE, days);
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    public static String getCertainDate(String dateString, int days, String format) {
        Calendar calendar = getCalendar(dateString);
        calendar.add(Calendar.DATE, days);
        return getStringFromDate(calendar.getTime(), format);
    }

    /**
     * 得到与指定日期相差指定天数的日期
     *
     * @param date 指定的日期
     * @param days 相差天数
     * @return
     */
    public static Date getCertainDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 得到与指定日期相差指定天数的日期字符串.<br>
     * <br>
     *
     * @param dateString 指定的日期.
     * @param period     前后的天数，正值为后， 负值为前.
     * @param periodType 周期类别 可以是天、月、年.
     * @return 日期字符串
     */
    public static String getCertainDate(String dateString, int period, int periodType) {
        Calendar calendar = getCalendar(dateString);

        switch (periodType) {
            case 1: // 天
                calendar.add(Calendar.DATE, period);
                break;
            case 2: // 月
                calendar.add(Calendar.MONTH, period);
                break;
            case 3: // 年
                calendar.add(Calendar.MONTH, period * 12);
                break;
            default:
        }
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 某日期（带时间）加上几天得到另外一个日期(带时间).<br>
     * <br>
     *
     * @param datetime 需要调整的日期(带时间).
     * @param days     调整天数.
     * @return 调整后的日期(带时间)
     */
    public static String getCertainDatetime(String datetime, int days) {
        Date curDate = getDateFromString(datetime, FORMAT_DATETIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DATE, days);
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME);
    }

    /**
     * 得到与当前日期相差指定月数的日期字符串.
     *
     * @param dif 前后的月数，正值为后， 负值为前.
     * @return 日期字符串
     */
    public static String getCertainMonth(int dif) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, dif);
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 得到与当前日期相差指定月数的日期字符串.
     *
     * @param dif    前后的月数，正值为后， 负值为前.
     * @param format 格式
     * @return 日期字符串
     */
    public static String getCertainMonth(int dif, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, dif);
        return getStringFromDate(calendar.getTime(), format);
    }

    /**
     * 得到当前日期的中文日期字符串.<br>
     * <br>
     *
     * @return 中文日期字符串
     */
    public static String getChineseDate() {
        return getChineseDate(getDate());
    }

    /**
     * 根据日期值得到中文日期字符串.<br>
     * <br>
     *
     * @param date 日期值.
     * @return 中文日期字符串
     */
    public static String getChineseDate(String date) {
        if (date.length() < Integer.valueOf("10")) {
            return "";
        } else {
            String year = date.substring(0, 4); // 年
            String month = date.substring(5, 7); // 月
            String day = date.substring(8, 10); // 日
            String y1 = year.substring(0, 1); // 年 字符1
            String y2 = year.substring(1, 2); // 年 字符1
            String y3 = year.substring(2, 3); // 年 字符3
            String y4 = year.substring(3, 4); // 年 字符4
            String m2 = month.substring(1, 2); // 月 字符2
            String d1 = day.substring(0, 1); // 日 1
            String d2 = day.substring(1, 2); // 日 2
            String cy1 = getChineseNum(y1);
            String cy2 = getChineseNum(y2);
            String cy3 = getChineseNum(y3);
            String cy4 = getChineseNum(y4);
            String cm2 = getChineseNum(m2);
            String cd1 = getChineseNum(d1);
            String cd2 = getChineseNum(d2);
            String cYear = cy1 + cy2 + cy3 + cy4 + "年";
            String cMonth = "月";

            if (Integer.parseInt(month) > 9) {
                cMonth = "十" + cm2 + cMonth;
            } else {
                cMonth = cm2 + cMonth;
            }

            String cDay = "日";

            if (Integer.parseInt(day) > 9) {
                cDay = cd1 + "十" + cd2 + cDay;
            } else {
                cDay = cd2 + cDay;
            }

            String chineseday = cYear + cMonth + cDay;
            return chineseday;
        }
    }

    /**
     * 得到当前日期的星期数 : 例如 '星期一', '星期二'等.<br>
     * <br>
     *
     * @return 当前日期的星期数
     */
    public static String getChineseDayOfWeek() {
        return getChineseDayOfWeek(getDate());
    }

    /**
     * 得到指定日期的星期数.<br>
     * <br>
     *
     * @param strDate 指定日期字符串.
     * @return 指定日期的星期数
     */
    public static String getChineseDayOfWeek(String strDate) {
        Calendar calendar = getCalendar(strDate);

        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String strWeek = "";

        switch (week) {
            case Calendar.SUNDAY:
                strWeek = "星期日";
                break;
            case Calendar.MONDAY:
                strWeek = "星期一";
                break;
            case Calendar.TUESDAY:
                strWeek = "星期二";
                break;
            case Calendar.WEDNESDAY:
                strWeek = "星期三";
                break;
            case Calendar.THURSDAY:
                strWeek = "星期四";
                break;
            case Calendar.FRIDAY:
                strWeek = "星期五";
                break;
            case Calendar.SATURDAY:
                strWeek = "星期六";
                break;
            default:
                strWeek = "星期一";
                break;
        }

        return strWeek;
    }

    /**
     * 根据数字得到中文数字.<br>
     * <br>
     *
     * @param number 数字.
     * @return 中文数字
     */
    public static String getChineseNum(String number) {
        String chinese = "";
        int x = Integer.parseInt(number);

        switch (x) {
            case 0:
                chinese = "零";
                break;
            case 1:
                chinese = "一";
                break;
            case 2:
                chinese = "二";
                break;
            case 3:
                chinese = "三";
                break;
            case 4:
                chinese = "四";
                break;
            case 5:
                chinese = "五";
                break;
            case 6:
                chinese = "六";
                break;
            case 7:
                chinese = "七";
                break;
            case 8:
                chinese = "八";
                break;
            case 9:
                chinese = "九";
                break;
            default:
        }
        return chinese;
    }

    /**
     * 根据日期值得到中文日期字符串.<br>
     * <br>
     *
     * @param date 给定日期.
     * @return 2005年09月23日格式的日期
     */
    public static String getChineseTwoDate(String date) {
        if (date.length() < 10) {
            return "";
        } else {
            String year = date.substring(0, 4); // 年
            String month = date.substring(5, 7); // 月
            String day = date.substring(8, 10); // 日

            return year + "年" + month + "月" + day + "日";
        }
    }

    /**
     * 自定义当前时间格式.<br>
     * <br>
     *
     * @param customFormat 自定义格式
     * @return 日期时间字符串
     */
    public static String getCustomDateTime(String customFormat) {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), customFormat);
    }

    /**
     * 得到当前的日期字符串.<br>
     * <br>
     *
     * @return 日期字符串
     */
    public static String getDate() {
        return getDate(Calendar.getInstance());
    }

    /**
     * 得到指定日期的字符串.<br>
     * <br>
     *
     * @param calendar 指定的日期.
     * @return 日期字符串
     */
    public static String getDate(Calendar calendar) {
        return getStringFromDate(calendar.getTime(), FORMAT_DATE);
    }

    /**
     * 某日期加上几天得到另外一个日期.<br>
     * <br>
     *
     * @param addNum  要增加的天数.
     * @param getDate 某日期.
     * @return 与某日期相差addNum天的日期
     */
    public static String getDateAdded(String getDate, int addNum) {
        return getCertainDate(getDate, addNum);
    }


    public static Date getDateOffset(Date date, int offset) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, offset);

        return calendar.getTime();
    }

    /**
     * 将指定格式的字符串格式化为日期.<br>
     * <br>
     *
     * @param s 字符串内容.
     * @return 日期
     */
    public static Date getDateFromString(String s) {
        return getDateFromString(s, FORMAT_DATE);
    }

    /**
     * 将指定格式的字符串格式化为日期.<br>
     * <br>
     *
     * @param s      字符串内容.
     * @param format 字符串格式.
     * @return 日期
     */
    public static Date getDateFromString(String s, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getDateTimeFromDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(date);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 得到当前的日期时间字符串.<br>
     * <br>
     *
     * @return 日期时间字符串
     */
    public static String getDatetime() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME);
    }

    /**
     * 得到当前的日期时间字符串,到小时分钟.<br>
     * <br>
     *
     * @return 日期时间字符串
     */
    public static String getDateTimeHm() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME_HM);
    }

    /**
     * 得到当前的日期时间字符串.<br>
     * <br>
     *
     * @return 日期时间字符串
     */
    public static String getDatetimeW3C() {
        return getDate() + "T" + getTime();
    }

    /**
     * 得到当前的日期时间字符串.<br>
     * <br>
     *
     * @return 日期时间字符串
     */
    public static String getDatetimeZd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return getStringFromDate(calendar.getTime(), FORMAT_DATETIME);
    }

    /**
     * 得到当前的日期.<br>
     * <br>
     *
     * @return 当前日期
     */
    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取相差时间，精确到分钟.<br>
     * <br>
     *
     * @param beginTime 开始时间.
     * @param endTime   结束时间.
     * @return 相差时间
     */
    public static String getDiffTime(String beginTime, String endTime) {
        try {
            if (endTime == null || endTime.length() == 0) {
                endTime = getDatetime();
            }
            Date eTime = getDateFromString(endTime, FORMAT_DATETIME);
            Date bTime = getDateFromString(beginTime, FORMAT_DATETIME);
            long time = eTime.getTime() - bTime.getTime();
            StringBuffer sb = new StringBuffer();
            int day = (int) Math.floor(time / (double) (24 * 3600000));
            if (day > 0) {
                sb.append(day).append("天");
            }
            time = time % (24 * 3600000);
            int hour = (int) Math.floor(time / (double) 3600000);
            if (hour > 0) {
                sb.append(hour).append("小时");
            }
            time = time % 3600000;
            int minute = (int) Math.ceil(time / (double) 60000);
            if (minute > 0) {
                sb.append(minute).append("分钟");
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到本周星期一的日期.<br>
     * <br>
     *
     * @return 日期字符串
     */
    public static String getFirstDateOfWeek() {
        return getFirstDateOfWeek(getDate());
    }

    /**
     * 得到指定日期的星期一的日期.<br>
     * <br>
     *
     * @param dateString 日期字符串.
     * @return 本周星期一的日期
     */
    public static String getFirstDateOfWeek(String dateString) {
        Calendar calendar = getCalendar(dateString);
        int iCount;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            iCount = -6;
        } else {
            iCount = Calendar.MONDAY - calendar.get(Calendar.DAY_OF_WEEK);
        }

        return getCertainDate(dateString, iCount);
    }

    /**
     * 得到当前的全时间字符串，包含毫秒.<br>
     * <br>
     *
     * @return 日期时间字符串
     */
    public static String getFulltime() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_FULLTIME);
    }

    /**
     * 得到当前的月份.<br>
     * <br>
     *
     * @return 当前月份
     */
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 取得两日期间的月份差数.
     *
     * @param startDate 起始日期.
     * @param endDate   结束日期.
     * @return 月份差数
     */
    public static int getMonthDiff(String startDate, String endDate) {
        String[] startArray = startDate.split("-");
        String[] endArray = endDate.split("-");
        int startYear = Integer.parseInt(startArray[0]);
        int startMonth = Integer.parseInt(startArray[1]);
        int endYear = Integer.parseInt(endArray[0]);
        int endMonth = Integer.parseInt(endArray[1]);
        return Math.abs((endYear - startYear) * 12 + endMonth - startMonth);
    }

    /**
     * 当月第一天
     *
     * @return
     */
    public static Date getFirstDayOfMonth(Date targetDate) {
        Calendar gcLast = Calendar.getInstance();
        gcLast.setTime(targetDate);
        gcLast.set(Calendar.DATE, 1);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String day_first = df.format(gcLast.getTime());
        day_first = day_first + " 00:00:00";
        return getDateFromString(day_first, FORMAT_DATETIME);
    }


    /**
     * 当月最后一天
     *
     * @return
     */
    public static Date getLastDayOfMonth(Date targetDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String lastDay = df.format(calendar.getTime());
        lastDay = new StringBuffer().append(lastDay).append(" 23:59:59").toString();
        return getDateFromString(lastDay, FORMAT_DATETIME);
    }

    /**
     * 将日期格式化为指定的字符串.<br>
     * <br>
     *
     * @param d      日期.
     * @param format 输出字符串格式.
     * @return 日期字符串
     */
    public static String getStringFromDate(Date d, String format) {
        if (d == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    public static String getDateTimeFromString(String d, String format, String rtnFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        SimpleDateFormat rtnf = new SimpleDateFormat(rtnFormat);
        try {
            return rtnf.format(sdf.parse(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将日期格式化为字符串.<br>
     *
     * @param d 日期
     * @return 日期字符串
     */
    public static String getStringFromDate(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        return sdf.format(d);
    }

    /**
     * 得到当前的纯时间字符串.<br>
     * <br>
     *
     * @return 时间字符串
     */
    public static String getTime() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_TIME);
    }

    /**
     * 如果当前日期是周六或者周日，则返回下周一的日期.<br>
     * <br>
     *
     * @param date 当前日期.
     * @return 下周一日期
     */
    public static String getWorkDate(final String date) {
        Date curDate = getDateFromString(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        int week = calendar.get(Calendar.DAY_OF_WEEK);

        if (week == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, 2);
        } else if (week == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        return getDate(calendar);
    }

    /**
     * 得到当前的年份.<br>
     * <br>
     *
     * @return 当前年份
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 得到当前的年月日期字符串.<br>
     * <br>
     *
     * @return 年月日期字符串
     */
    public static String getYearMonth() {
        Calendar calendar = Calendar.getInstance();
        return getStringFromDate(calendar.getTime(), FORMAT_YEARMONTH);
    }

    /**
     * 当前日期与参数传递的日期的相差天数.<br>
     * <br>
     *
     * @param dateinfo 指定的日期.
     * @return 相差的天数
     */
    public static int selectDateDiff(String dateinfo) {
        return selectDateDiff(dateinfo, getDate());
    }

    /**
     * 当得到两个日期相差天数.<br>
     * <br>
     *
     * @param first  第一个日期.
     * @param second 第二个日期.
     * @return 相差的天数
     */
    public static int selectDateDiff(String first, String second) {
        int dif = 0;
        try {
            Date fDate = getDateFromString(first, FORMAT_DATE);
            Date sDate = getDateFromString(second, FORMAT_DATE);
            dif = (int) ((fDate.getTime() - sDate.getTime()) / 86400000);
        } catch (Exception e) {
            dif = 0;
        }
        return dif;
    }

    /**
     * @param start
     * @param end
     * @param date_formate
     * @return long
     * @说明计算两个日期的时间相差毫秒数
     */

    public static long selectMillSecDiff(String start, String end, String date_formate) {
        SimpleDateFormat dfs = new SimpleDateFormat(date_formate);
        long between = 0;
        try {
            Date begin_date = dfs.parse(start);
            Date end_date = dfs.parse(end);
            between = (end_date.getTime() - begin_date.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return between;
    }

    /**
     * <p>Description:获取当前日期</p>
     *
     * @return 当前日期
     * @Title: getCurrentDate
     * @author maqingrong
     */
    public static String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }

    /**
     * <p>Description:获取当前日期</p>
     *
     * @param pattern 转换模式
     * @return 当前日期
     * @Title: getCurrentDate
     * @author maqingrong
     */
    public static String getCurrentDate(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }

    /**
     * <p>Description:获取当前时间</p>
     *
     * @return 当前时间
     * @Title: getCurrentTime
     * @author maqingrong
     */
    public static String getCurrentTime() {
        DateFormat df = new SimpleDateFormat("HHmmss");
        return df.format(new Date());
    }

    /**
     * <p>Description:字符串转换为时间</p>
     *
     * @return 转换日期
     * @Title: string2Date
     * @author maqingrong
     */
    public static Date string2Date(String date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * <p>Description:字符串转换为时间</p>
     *
     * @param date    日期字符串
     * @param pattern 转换模式
     * @return
     * @Title: string2Date
     * @author maqingrong
     */
    public static Date string2Date(String date, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param
     * @return String 返回类型
     * @throws
     * @Title: initDate
     * @Description: 将当期日期时间转换为当日初始日期时间，如2014-12-02 11:58:59 转换为2014-12-02 00:00:00
     */
    public static String initDate(Date dt) {
        DateFormat df = new SimpleDateFormat(FORMAT_DATE);
        return df.format(dt) + " 00:00:00";

    }

    /**
     * 获取一个时间点前、后的一个时间  正为后  负为前
     *
     * @param date
     * @param min
     * @return
     */
    public static Date getMinuteDate(Date date, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//设置当前日期
        calendar.add(Calendar.MINUTE, min);//﹣时间查询当前时间之前
        return calendar.getTime();
    }

    /**
     * 获取指定日期的 00:00:00
     *
     * @param date
     * @return
     */
    public static String getMinTimeOfDay(Date date) {
        return getStringFromDate(date, FORMAT_DATE) + " 00:00:00";
    }

    /**
     * 获取指定日期的 23:59:59
     *
     * @param date
     * @return
     */
    public static String getMaxTimeOfDay(Date date) {
        return getStringFromDate(date, FORMAT_DATE) + " 23:59:59";
    }

    /**
     * 字符串是否符合日期格式
     *
     * @param str
     * @param format
     * @return
     */
    public static boolean isValidDate(String str, String format) {
        boolean isValidDate = false;
        try {
            Date date = getDateFromString(str, format);
            if (date != null) {
                isValidDate = true;
            }
        } catch (Exception e) {
            isValidDate = false;
        }

        return isValidDate;
    }

    /**
     * 获取指定日期的 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getMinTime(Date date) {

        String minTime = getStringFromDate(date, FORMAT_DATE) + " 00:00:00";
        return getDateFromString(minTime, FORMAT_DATETIME);
    }

    /**
     * 获取指定日期的 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getMaxTime(Date date) {
        String maxTime = getStringFromDate(date, FORMAT_DATE) + " 23:59:59";
        return getDateFromString(maxTime, FORMAT_DATETIME);
    }

    public static Boolean compareDate(Date startDate, Date endDate) {
        Date nowDate = new Date();
        if (nowDate.getTime() <= endDate.getTime() && nowDate.getTime() >= startDate.getTime()) {
            return true;
        }
        return false;
    }

    public static Boolean checkInvalid(Date endDate) {
        Date nowDate = new Date();
        if (nowDate.getTime() > endDate.getTime()) {
            return true;
        }
        return false;
    }


    /**
     * 取得两日期间的月份差数.
     *
     * @param startDate 起始月份(yyyyMM).
     * @param endDate   结束日期(yyyy-MM-dd).
     * @return 月份差数
     */
    public static int getMonthDifference(String startDate, String endDate) {
        String[] endArray = endDate.split("-");
        int startYear = Integer.parseInt(startDate.substring(0, 4));
        int startMonth = Integer.parseInt(startDate.substring(4, 6));
        int endYear = Integer.parseInt(endArray[0]);
        int endMonth = Integer.parseInt(endArray[1]);
        return (endYear - startYear) * 12 + endMonth - startMonth;
    }


    /**
     * 获取目标时间偏移对应小时后的时间（支持正向和负向）
     **/
    public static Date getDateHourAdded(Date sourceDateTime, int hourOffset) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(sourceDateTime);
        instance.add(Calendar.HOUR, hourOffset);
        sourceDateTime = instance.getTime();
        return sourceDateTime;
    }


    /**
     * 获取对应时区时间对应的北京时间（timezoneOffset有浏览器js getTimezoneOffset()获取，转为小时后的值）
     **/
    public static Date getBeijingTime(Date sourceDate, int timezoneOffset) {
        //时区偏移
        return getDateHourAdded(sourceDate, timezoneOffset + 8);
    }

    /**
     * 获取对应时区时间对应的北京时间（timezoneOffset有浏览器js getTimezoneOffset()获取，转为小时后的值）
     **/
    public static String getBeijingTime(String sourceDateStr, int timezoneOffset) {
        //时区偏移
        Date sourceDate = getDateFromString(sourceDateStr, FORMAT_DATETIME);
        Date dateHourAdded = getDateHourAdded(sourceDate, timezoneOffset + 8);
        return getStringFromDate(dateHourAdded, FORMAT_DATETIME);
    }

    public static Date getHourBefore(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        /* HOUR_OF_DAY 指示一天中的小时 */
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - i);
        return calendar.getTime();
    }

    /**
     * 分的转换
     * @param i
     * @return
     */
    public static String getMinute(int i) {
        String minute;
        if (i >= 0 && i < 10) {
            minute = "00";
        } else if (i >= 10 && i < 20) {
            minute = "10";
        } else if (i >= 20 && i < 30) {
            minute = "20";
        } else if (i >= 30 && i < 40) {
            minute = "30";
        } else if (i >= 40 && i < 50) {
            minute = "40";
        } else {
            minute = "50";
        }
        return minute;
    }

    public static Date getDayBeginTime(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getDayEndTime(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();

    }

    public static int getNowHour() {
        Calendar calendar = Calendar.getInstance();
        int curHour24 = calendar.get(Calendar.HOUR_OF_DAY);
        return curHour24;
    }

    public static int getBeforeOneHour() {
        Calendar calendar = Calendar.getInstance();
        int curHour24 = calendar.get(Calendar.HOUR_OF_DAY);
        if (curHour24 == 0) {
           return 23;
        } else {
            return curHour24 - 1;
        }
    }

    public static String getMonthByMonthTime(String monthTime) {
        if ("1".equals(monthTime) || "01".equals(monthTime)) {
            return "一月";
        } else if ("2".equals(monthTime) || "02".equals(monthTime)) {
            return "二月";
        } else if ("3".equals(monthTime) || "03".equals(monthTime)) {
            return "三月";
        } else if ("4".equals(monthTime) || "04".equals(monthTime)) {
            return "四月";
        } else if ("5".equals(monthTime) || "05".equals(monthTime)) {
            return "五月";
        } else if ("6".equals(monthTime) || "06".equals(monthTime)) {
            return "六月";
        } else if ("7".equals(monthTime) || "07".equals(monthTime)) {
            return "七月";
        } else if ("8".equals(monthTime) || "08".equals(monthTime)) {
            return "八月";
        } else if ("9".equals(monthTime) || "09".equals(monthTime)) {
            return "九月";
        } else if ("10".equals(monthTime)) {
            return "十月";
        } else if ("11".equals(monthTime)) {
            return "十一月";
        } else if ("12".equals(monthTime)) {
            return "十二月";
        }
        return null;
    }

    /**
     * 获取N个月前的第一天
     * @param month
     */
    public static String getBeforeMonthDate(int month){
        Calendar no = Calendar.getInstance();
        no.setTime(new Date());
        no.set(Calendar.MONTH, no.get(Calendar.MONTH) - month);
        Date time = no.getTime();
        Date firstDayOfMonth = getFirstDayOfMonth(time);
        String stringFromDate = getStringFromDate(firstDayOfMonth, FORMAT_DATE);
        return stringFromDate;
    }

    public static String getHourMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String minuteNew = DateHandlerUtil.getMinute(minute);
        return hour < 10 ? "0" + hour + ":" + minuteNew : hour + ":" + minuteNew;
    }

    public static String getHour0String(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour < 10 ? "0" + hour + ":00" : hour + ":00";
    }

    public static Date setMinuteTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int minute = calendar.get(Calendar.MINUTE);
        String minuteNew = DateHandlerUtil.getMinute(minute);
        calendar.set(Calendar.MINUTE, Integer.valueOf(minuteNew));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public static Date setHourTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 当前时间2020-09-07 17:31:18
     * 返回[16:30, 16:40, 16:50, 17:00, 17:10, 17:20, 17:30]
     * @param nowDate
     * @return
     */
    public static List<String> getHourMinuteTimeList(Date nowDate) {
        List<String> timeList1 = new ArrayList<>();
        Date minuteEnd = DateHandlerUtil.setMinuteTime(nowDate);//2020-09-07 13:40:00:00
        int m;
        for (int i = 6; i >= 0; i--) {
            Date minuteBefore = DateHandlerUtil.getMinuteBefore(minuteEnd, i * 10);//2020-09-07 13:30
            String dateTime = DateHandlerUtil.getStringFromDate(minuteBefore, DateHandlerUtil.FORMAT_HOUR_MINUTE_TIME);
            timeList1.add(dateTime);
        }
        return timeList1;
    }

    public static List<String> getHour0MinuteTimeList(Date nowDate) {
        List<String> timeList1 = new ArrayList<>();
//        String nowDateStr = DateHandlerUtil.getStringFromDate(nowDate, DateHandlerUtil.FORMAT_DATE);//2020-09-03
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        for (int i = 0; i <= hour; i++) {
            timeList1.add(i < 10 ? "0" + i + ":00" : i + ":00");
//            timeList1.add(i < 10 ? nowDateStr + " 0" + i + ":00" : nowDateStr + " " + i + ":00");
        }
        return timeList1;
    }

    public static Date getZeroTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取过去m天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static ArrayList<String> getBetweenDays(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = intervals -1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

}
