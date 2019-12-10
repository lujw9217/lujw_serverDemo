package com.example.serverdemo.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author : Lujw
 * @Class Name   : DateUtil
 * @Description : TODO
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util
 * @date : 2019/12/10 16:00
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class DateUtil {


    /**
     * 北京时间 时区
     **/
    public static final String TIMEZONE_GMT8 = "GMT+08:00";

    public static final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String DATE_FORMAT_MIDDLE = "yyyy-MM-dd HH:mm";

    /**
     * yyyy-MM-dd HHmm
     */
    public static final String DATE_FORMAT_MIDDLE_NOCOMMA = "yyyy-MM-dd HHmm";

    /**
     * yyyy-MM-dd HHmmss
     */
    public static final String DATE_FORMAT_LONG_NOCOMMA = "yyyy-MM-dd HHmmss";

    /**
     * yyyy-MM-dd
     */
    public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";

    /**
     * yyyyMMdd
     */
    public static final String DATE_FORMAT_SHORT2 = "yyyyMMdd";

    /**
     * yyyy/MM/dd
     */
    public static final String DATE_FORMAT_SHORT3 = "yyyy/MM/dd";

    /**
     * yyyy.MM.dd
     */
    public static final String DATE_FORMAT_SHORT4 = "yyyy.MM.dd";

    /**
     * ddMMMyy
     */
    public static final String DATE_FORMAT_SHORT5 = "ddMMMyy";

    /**
     * yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_SHORT6 = "yyyyMMddHHmmss";

    /**
     * yyyyMM
     */
    public static final String DATE_FORMAT_SHORT7 = "yyyyMM";

    /**
     * yyyy
     */
    public static final String DATE_FORMAT_SHORT8 = "yyyy";

    /**
     * yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_SHORT_CH7 = "yyyy年MM月dd日";

    /**
     * yyyy年MM月dd日 HH时mm分
     */
    public static final String DATE_FORMAT_MIDDLE_CH8 = "yyyy年MM月dd日 HH时mm分";
    /**
     * 星期
     **/
    private final static String[] DAYNAMES = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    /**
     * 月份
     **/
    private static final String[] MONTH_NO = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    /**
     * 英文月份
     **/
    private static final String[] MONTH_EN = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

   /**
    * @description   : 将字符串转化成"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"格式
    * @method_name   : getOrderDate
    * @param         : [timeStr]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 16:01
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static String getOrderDate(String timeStr) throws Exception {
        if (BaseUtil.stringNotNull(timeStr)) {
            Date date = DateUtil.stringToDate(timeStr, DateUtil.DATE_FORMAT_LONG);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return sdf.format(date);
        }
        return null;
    }

    /**
     * 获取当前日期（JAVA新特性）
     *
     * @param : []
     * @return : java.lang.String
     * @throws :
     * @description : TODO
     * @method_name : getNowDate
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String getNowDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
//        localDateTime = localDateTime.plusYears(1);
        return localDateTime.atZone(ZoneOffset.of("+8")).format(formatter);
    }

    /**
     * 在传入的时间基础上加减n个小时
     *
     * @param curDate 基础时间
     * @param hours   要增减的小时 负数表示减去，正数表示加上
     * @return
     */
    public static Date operateHours(Date curDate, int hours) {
        long millSeconds = 1000L * 60 * 60 * hours;

        return new Date(curDate.getTime() + millSeconds);
    }

    /**
     * 获取UTC时间，utc时间比我们所在的东八区晚8个小时
     *
     * @return Date utc时间
     */
    public static Date getUTCTime() {
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime();
    }

   /**
    * @description   : 获取localDateTime对象（北京时间）
    * @method_name   : getLocalDateTime
    * @param         : [millSeconds]
    * @return        : java.time.LocalDateTime
    * @throws        :
    * @date          : 2019/12/10 16:02
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static LocalDateTime getLocalDateTime(long millSeconds) {
        return LocalDateTime.ofEpochSecond(millSeconds / 1000, 0, ZoneOffset.ofHours(8));
    }

    /**
     * @description   : 将localdateTime 转化为String
     * @method_name   : localDateTimeFormat
     * @param         : [dateTime, pattern]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 16:02
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String localDateTimeFormat(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

   /**
    * @description   : 将localdateTime 转化为String
    * @method_name   : localDateFormat
    * @param         : [dateTime, pattern]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 16:02
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static String localDateFormat(LocalDate dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

/**
 * @description   : 获取月份的第一天
 * @method_name   : getFirstDayOfMonth
 * @param         : [dateTime, pattern]
 * @return        : java.lang.String
 * @throws        :
 * @date          : 2019/12/10 16:02
 * @author        : Lujw
 * @update date   :
 * @update author :
 */
    public static String getFirstDayOfMonth(LocalDate dateTime, String pattern) {
        return localDateFormat(LocalDate.of(dateTime.getYear(), dateTime.getMonth(), 1), pattern);
    }

    /**
     * @description   : 获取月份的第一天
     * @method_name   : getLastDayOfMonth
     * @param         : [dateTime, pattern]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 16:02
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String getLastDayOfMonth(LocalDate dateTime, String pattern) {
        return localDateFormat(dateTime.with(TemporalAdjusters.lastDayOfMonth()), pattern);
    }

  /**
   * @description   : 获取Date 时区对应的 string格式串
   * @method_name   : getDateInTimeZone
   * @param         : [currentDate, timeZoneId, formatStr]
   * @return        : java.lang.String
   * @throws        :
   * @date          : 2019/12/10 16:03
   * @author        : Lujw
   * @update date   :
   * @update author :
   */
    public static String getDateInTimeZone(final Date currentDate, final String timeZoneId, String formatStr) {

        String timeZone = timeZoneId;
        if (timeZoneId == null || "".equals(timeZoneId.trim())) {
            timeZone = TIMEZONE_GMT8;
        }

        Calendar mbCal = new GregorianCalendar(TimeZone.getTimeZone(timeZone));
        mbCal.setTimeInMillis(currentDate.getTime());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, mbCal.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, mbCal.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, mbCal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, mbCal.get(Calendar.MILLISECOND));

        DateFormat sdf = new SimpleDateFormat(formatStr);

        return sdf.format(cal.getTime());
    }

/**
 * @description   : 获取 日期 对应时区 日期
 * @method_name   : getDateInTimeZone
 * @param         : [currentDate, timeZoneId]
 * @return        : java.util.Date
 * @throws        :
 * @date          : 2019/12/10 16:03
 * @author        : Lujw
 * @update date   :
 * @update author :
 */
    public static Date getDateInTimeZone(final Date currentDate, final String timeZoneId) {

        String timeZone = timeZoneId;
        if (timeZoneId == null || "".equals(timeZoneId.trim())) {
            timeZone = TIMEZONE_GMT8;
        }

        Calendar mbCal = new GregorianCalendar(TimeZone.getTimeZone(timeZone));
        mbCal.setTimeInMillis(currentDate.getTime());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, mbCal.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, mbCal.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, mbCal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, mbCal.get(Calendar.MILLISECOND));

        return cal.getTime();
    }

    /**
     * @description   : 日期 对应时区 日期
     * @method_name   : formatDateInTimeZone
     * @param         : [date, timeZoneId, format]
     * @return        : java.util.Date
     * @throws        :
     * @date          : 2019/12/10 16:03
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static Date formatDateInTimeZone(final String date, final String timeZoneId, final String format) throws Exception {
        if (date == null || date.trim() == "") {
            return null;
        }

        String timeZone = timeZoneId;
        if (timeZoneId == null || "".equals(timeZoneId.trim())) {
            timeZone = TIMEZONE_GMT8;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setTimeZone(TimeZone.getTimeZone(timeZone));
        return fmt.parse(date);
    }

    /**
     * @description   : 计算给定的2个日期相差天数
     * @method_name   : calculateDays1
     * @param         : [date1, date2]
     * @return        : int
     * @throws        :
     * @date          : 2019/12/10 16:03
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static int calculateDays1(String date1, String date2) {
        if (date1 == null || date1.length() == 0 || date2 == null || date2.length() == 0) {
            return 0;
        }
        int count = 0;
        String year1 = date1.substring(0, 4);
        String month1 = date1.substring(5, 7);
        String day1 = date1.substring(8, 10);
        String year2 = date2.substring(0, 4);
        String month2 = date2.substring(5, 7);
        String day2 = date2.substring(8, 10);

        Calendar cale1 = new GregorianCalendar(Integer.valueOf(year1).intValue(), Integer.valueOf(month1).intValue(),
                Integer.valueOf(day1).intValue());
        Calendar cale2 = new GregorianCalendar(Integer.valueOf(year2).intValue(), Integer.valueOf(month2).intValue(),
                Integer.valueOf(day2).intValue());

        long date1Millis = cale1.getTimeInMillis();
        long date2Millis = cale2.getTimeInMillis();

        long diff = date2Millis - date1Millis;
        count = (int) (diff / (24 * 60 * 60 * 1000));
        return count;
    }

   /**
    * @description   : 计算给定的2个日期相差天数
    * @method_name   : calculateDays2
    * @param         : [date1, date2]
    * @return        : int
    * @throws        :
    * @date          : 2019/12/10 16:03
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static int calculateDays2(String date1, String date2) throws Exception {
        if (date1 == null || date1.length() == 0 || date2 == null || date2.length() == 0) {
            return 0;
        }
        int count = 0;
        DateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date dat1 = sf.parse(date1);
        Date dat2 = sf.parse(date2);
        long date1Millis = dat1.getTime();
        long date2Millis = dat2.getTime();
        long diff = date2Millis - date1Millis;
        count = (int) (diff / (24 * 60 * 60 * 1000));
        return count;
    }
/**
 * @description   : 计算给定的2个日期相差天数
 * @method_name   : calculateDaysForDate
 * @param         : [date1, date2]
 * @return        : int
 * @throws        :
 * @date          : 2019/12/10 16:03
 * @author        : Lujw
 * @update date   :
 * @update author :
 */
    public static int calculateDaysForDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        int count = 0;

        long date1Millis = date1.getTime();
        long date2Millis = date2.getTime();
        long diff = date2Millis - date1Millis;
        count = (int) (diff / (24 * 60 * 60 * 1000));

        return count;
    }

    /**
     * @description   : 算指定日期是星期几
     * @method_name   : calculateWeek
     * @param         : [date]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 16:03
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String calculateWeek(String date) throws Exception {
        if (date == null || date.length() == 0) {
            return "";
        }
        int dayOfWeek = 0;
        DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date dat = sf.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dat);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        return DAYNAMES[dayOfWeek - 1];
    }

    /**
     * @description   : 将数字月份转换成英文格式
     * @method_name   : monthToEN
     * @param         : [monthNO]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 16:04
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String monthToEN(String monthNO) {
        String monthEN = null;
        if (monthNO.equalsIgnoreCase(MONTH_NO[0]) || MONTH_NO[0].substring(1).equals(monthNO)) {
            monthEN = MONTH_EN[0];
        }
        if (monthNO.equalsIgnoreCase(MONTH_NO[1]) || MONTH_NO[1].substring(1).equals(monthNO)) {
            monthEN = MONTH_EN[1];
        }
        if (monthNO.equalsIgnoreCase(MONTH_NO[2]) || MONTH_NO[2].substring(1).equals(monthNO)) {
            monthEN = MONTH_EN[2];
        }
        if (monthNO.equalsIgnoreCase(MONTH_NO[3]) || MONTH_NO[3].substring(1).equals(monthNO)) {
            monthEN = MONTH_EN[3];
        }
        if (monthNO.equalsIgnoreCase(MONTH_NO[4]) || MONTH_NO[4].substring(1).equals(monthNO)) {
            monthEN = MONTH_EN[4];
        }
        if (monthNO.equalsIgnoreCase(MONTH_NO[5]) || MONTH_NO[5].substring(1).equals(monthNO)) {
            monthEN = MONTH_EN[5];
        }
        if (monthNO.equalsIgnoreCase(MONTH_NO[6]) || MONTH_NO[6].substring(1).equals(monthNO)) {
            monthEN = MONTH_EN[6];
        }
        if (monthNO.equalsIgnoreCase(MONTH_NO[7]) || MONTH_NO[7].substring(1).equals(monthNO)) {
            monthEN = MONTH_EN[7];
        }
        if (monthNO.equalsIgnoreCase(MONTH_NO[8]) || MONTH_NO[8].substring(1).equals(monthNO)) {
            monthEN = MONTH_EN[8];
        }
        if (monthNO.equalsIgnoreCase(MONTH_NO[9]) || MONTH_NO[9].substring(1).equals(monthNO)) {
            monthEN = MONTH_EN[9];
        }
        if (monthNO.equalsIgnoreCase(MONTH_NO[10])) {
            monthEN = MONTH_EN[10];
        }
        if (monthNO.equalsIgnoreCase(MONTH_NO[11])) {
            monthEN = MONTH_EN[11];
        }
        return monthEN;
    }
/**
 * @description   : 将英文月份转换成数字格式
 * @method_name   : monthToNO
 * @param         : [monthEN]
 * @return        : java.lang.String
 * @throws        :
 * @date          : 2019/12/10 16:04
 * @author        : Lujw
 * @update date   :
 * @update author :
 */
    public static String monthToNO(String monthEN) {
        String monthNO = null;
        if (monthEN.equalsIgnoreCase(MONTH_EN[0])) {
            monthNO = MONTH_NO[0];
        }
        if (monthEN.equalsIgnoreCase(MONTH_EN[1])) {
            monthNO = MONTH_NO[1];
        }
        if (monthEN.equalsIgnoreCase(MONTH_EN[2])) {
            monthNO = MONTH_NO[2];
        }
        if (monthEN.equalsIgnoreCase(MONTH_EN[3])) {
            monthNO = MONTH_NO[3];
        }
        if (monthEN.equalsIgnoreCase(MONTH_EN[4])) {
            monthNO = MONTH_NO[4];
        }
        if (monthEN.equalsIgnoreCase(MONTH_EN[5])) {
            monthNO = MONTH_NO[5];
        }
        if (monthEN.equalsIgnoreCase(MONTH_EN[6])) {
            monthNO = MONTH_NO[6];
        }
        if (monthEN.equalsIgnoreCase(MONTH_EN[7])) {
            monthNO = MONTH_NO[7];
        }
        if (monthEN.equalsIgnoreCase(MONTH_EN[8])) {
            monthNO = MONTH_NO[8];
        }
        if (monthEN.equalsIgnoreCase(MONTH_EN[9])) {
            monthNO = MONTH_NO[9];
        }
        if (monthEN.equalsIgnoreCase(MONTH_EN[10])) {
            monthNO = MONTH_NO[10];
        }
        if (monthEN.equalsIgnoreCase(MONTH_EN[11])) {
            monthNO = MONTH_NO[11];
        }
        return monthNO;
    }
/**
 * @description   : 字符串转换到时间格式
 * @method_name   : stringToDate
 * @param         : [dateStr, formatStr]
 * @return        : java.util.Date
 * @throws        :
 * @date          : 2019/12/10 16:04
 * @author        : Lujw
 * @update date   :
 * @update author :
 */
    public static Date stringToDate(String dateStr, String formatStr) throws Exception {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = sdf.parse(dateStr);
        return date;
    }

  /**
   * @description   : 将时间转换成指定格式的字符串
   * @method_name   : dateFormat
   * @param         : [date, format]
   * @return        : java.lang.String
   * @throws        :
   * @date          : 2019/12/10 16:04
   * @author        : Lujw
   * @update date   :
   * @update author :
   */
    public static String dateFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

   /**
    * @description   : 将时间字符串转换成指定格式的字符串
    * @method_name   : dateFormat
    * @param         : [dateStr, originFormat, format]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 16:04
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static String dateFormat(String dateStr, String originFormat, String format) throws Exception {
        Date date = stringToDate(dateStr, originFormat);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

 /**
  * @description   : 获取当前时间的字符串格式
  * @method_name   : getNowDateTime
  * @param         : [format]
  * @return        : java.lang.String
  * @throws        :
  * @date          : 2019/12/10 16:04
  * @author        : Lujw
  * @update date   :
  * @update author :
  */
    public static String getNowDateTime(String format) {
        return dateFormat(new Date(), format);
    }

   /**
    * @description   : 获取n分钟后的时间字符串
    * @method_name   : getMinutesLater
    * @param         : [dateStr, format, minutes]
    * @return        : java.util.Date
    * @throws        :
    * @date          : 2019/12/10 16:04
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static Date getMinutesLater(String dateStr, String format, int minutes) throws Exception {
        long millSeconds = minutes * 60 * 1000L;
        Date date = stringToDate(dateStr, format);
        return new Date(date.getTime() + millSeconds);
    }

/**
 * @description   : 根据日期方言将字符串转换成日期
 * @method_name   : stringToDateByLocale
 * @param         : [dateStr, formatStr, locale]
 * @return        : java.util.Date
 * @throws        :
 * @date          : 2019/12/10 16:04
 * @author        : Lujw
 * @update date   :
 * @update author :
 */
    public static Date stringToDateByLocale(String dateStr, String formatStr, Locale locale) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat(formatStr, locale);
        Date date = dateFormat.parse(dateStr);
        return date;
    }

    /**
     * @description   : Java计算两个日期时间相差几天,几小时,几分钟
     * @method_name   : dateDiff
     * @param         : [startTime, endTime, format]
     * @return        : long
     * @throws        :
     * @date          : 2019/12/10 16:05
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static long dateDiff(String startTime, String endTime, String format) throws Exception {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long diff;
        Long longTime = null;
        // 获得两个时间的毫秒时间差异
        diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
        long day = diff / nd;// 计算差多少天
        long hour = diff % nd / nh;// 计算差多少小时
        long min = diff % nd % nh / nm;// 计算差多少分钟
        // 输出结果
        // System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟" + sec + "秒。");
        longTime = day * 24 + hour + min / 60;
        return longTime;

    }

    /**
     * @description   : 获得指定日期的前几天
     * @method_name   : getSpecifiedDayBefore
     * @param         : [specifiedDay, days]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 16:05
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String getSpecifiedDayBefore(String specifiedDay, int days) throws Exception {
        Calendar c = Calendar.getInstance();
        Date date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - days);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

   /**
    * @description   : 获得指定日期的后几天
    * @method_name   : getSpecifiedDayAfter
    * @param         : [specifiedDay, days]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 16:05
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static String getSpecifiedDayAfter(String specifiedDay, int days) throws Exception {
        Calendar c = Calendar.getInstance();
        Date date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + days);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }
/**
 * @description   : 将日期（yyyy-MM-dd HH:mm:ss）格式化为(yyyy-MM-ddTHH:mm:ss)
 * @method_name   : getAirPriceDate
 * @param         : [specifiedDay]
 * @return        : java.lang.String
 * @throws        :
 * @date          : 2019/12/10 16:05
 * @author        : Lujw
 * @update date   :
 * @update author :
 */
    public static String getAirPriceDate(String specifiedDay) {

        String temp[] = specifiedDay.split(" ");

        return temp[0] + "T" + temp[1];
    }
    /**
     * @description   : 计算 两个日期，相差的天数
     * @method_name   : subDateDay
     * @param         : [date1, date2]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 16:06
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String subDateDay(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // 第一个日期
            Date time1 = sdf.parse(date1);

            // 第二个日期
            Date time2 = sdf.parse(date2);

            // 日期
            Long diff = time1.getTime() - time2.getTime();
            // 得到两个日期之间 天数差值
            int diffDay = (int) (diff / (24 * 60 * 60 * 1000));
            if (diffDay == 0) {
                return "";
            } else if (diffDay > 0) {
                return "+" + diffDay;
            } else {
                return diffDay + "";
            }

        } catch (Exception e) {
            return date1 + date2;
        }
    }

    /**
     * @description   : 当前日期是否大于今天
     * @method_name   : isEarlyThanToday
     * @param         : [dateStr, dateFormat]
     * @return        : boolean
     * @throws        :
     * @date          : 2019/12/10 16:06
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean isEarlyThanToday(String dateStr, String dateFormat) throws ParseException {
        Date nowdate = new Date();
        if (!BaseUtil.stringNotNull(dateFormat)) {
            dateFormat = DateUtil.DATE_FORMAT_SHORT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date d = sdf.parse(dateStr);
        return d.before(nowdate);
    }


   /**
    * @description   : 获取n 月前日期
    * @method_name   : getDateStrMonthesAgo
    * @param         : [month, ptn]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 16:06
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static String getDateStrMonthesAgo(String month, String ptn) {
        ptn = BaseUtil.stringNotNull(ptn) ? ptn : DateUtil.DATE_FORMAT_SHORT;
        return dateFormat(getDateMonthesAgo(month), ptn);
    }

    /**
     * @description   :  获取n月前日期
     * @method_name   : getDateMonthesAgo
     * @param         : [month]
     * @return        : java.util.Date
     * @throws        :
     * @date          : 2019/12/10 16:06
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static Date getDateMonthesAgo(String month) {
        int m = Integer.valueOf(month);
        Date monthesAgo = null;

        // 计算n月前日期
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.MONTH, -m);
        monthesAgo = cd.getTime();

        return monthesAgo;
    }

    /**
     * @description   : 获取n 月前日期
     * @method_name   : getDateBefore
     * @param         : [month, ptn]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 16:07
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String getDateBefore(String month, String ptn) {
        ptn = BaseUtil.stringNotNull(ptn) ? ptn : "yyyy-MM-dd";
        int m = Integer.valueOf(month);
        Date dat = null;
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.MONTH, -m);
        dat = cd.getTime();
        SimpleDateFormat dformat = new SimpleDateFormat(ptn);
        String finalDate = dformat.format(dat);

        return finalDate;
    }

    /**
     * @description   : 比较两个时间字符串是否是同一天，是为true
     * @method_name   : isSameDay
     * @param         : [dateStr1, dateFormat1, dateStr2, dateFormat2]
     * @return        : boolean
     * @throws        :
     * @date          : 2019/12/10 16:07
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean isSameDay(String dateStr1, String dateFormat1, String dateStr2, String dateFormat2) throws Exception {
        Date date1 = stringToDate(dateStr1, dateFormat1);
        Date date2 = stringToDate(dateStr2, dateFormat2);
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(date1);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date2);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR) && calDateA.get(Calendar.MONTH) == calDateB
                .get(Calendar.MONTH) && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @description   :  比较两个时间字符串是否是同一天，是为true
     * @method_name   : isSameDay
     * @param         : [dateStr1, dateStr, dateFormat]
     * @return        : boolean
     * @throws        :
     * @date          : 2019/12/10 16:07
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean isSameDay(String dateStr1, String dateStr, String dateFormat) throws Exception {

        if (BaseUtil.stringNotNull(dateStr1) && dateStr1.contains(" ")) {
            dateStr1 = dateStr1.split(" ")[0];
        }
        Date date1 = stringToDate(dateStr1, DateUtil.DATE_FORMAT_SHORT);
        Date date2 = stringToDate(dateStr, dateFormat);

        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(date1);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date2);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR) && calDateA.get(Calendar.MONTH) == calDateB
                .get(Calendar.MONTH) && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }

  /**
   * @description   :  判断第二个日期是否大于第一个日期 true：大于等于， false：若firstDate <= sndDate 则返回true 反之为 false
   * @method_name   : sndLaterThanFirst
   * @param         : [firstDate, sndDate]
   * @return        : boolean
   * @throws        :
   * @date          : 2019/12/10 16:07
   * @author        : Lujw
   * @update date   :
   * @update author :
   */
    public static boolean sndLaterThanFirst(Date firstDate, Date sndDate) {

        long firstTime = firstDate.getTime();
        long sndTime = sndDate.getTime();
        return firstTime <= sndTime;

    }

 /**
  * @description   : 将日期字符串转（yyyy-MM-dd）换成（dd month_en yy）
  * @method_name   : formateSpecial
  * @param         : [dateStr]
  * @return        : java.lang.String
  * @throws        :
  * @date          : 2019/12/10 16:08
  * @author        : Lujw
  * @update date   :
  * @update author :
  */
    public static String formateSpecial(String dateStr) throws Exception {
        Date date = stringToDate(dateStr, DateUtil.DATE_FORMAT_SHORT);
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_SHORT);
        String[] dstr = sdf.format(date).split("-");
        return dstr[2] + " " + monthToEN(dstr[1]) + " " + dstr[0];
    }

   /**
    * @description   : 将日期字符串转（yyyy-MM-dd HH:mm）换成（HH:mm）
    * @method_name   : formateSuperShort
    * @param         : [dateStr]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 16:08
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static String formateSuperShort(String dateStr) throws Exception {
        Date date = stringToDate(dateStr, DateUtil.DATE_FORMAT_MIDDLE);
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_MIDDLE);
        String[] dstr = sdf.format(date).split(" ");
        return dstr[1];
    }

    /**
     * @description   : 获取两个日期间隔的所有日期
     * @method_name   : getBetweenDate
     * @param         : [start, end]
     * @return        : java.util.List<java.lang.String>
     * @throws        :
     * @date          : 2019/12/10 16:08
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static List<String> getBetweenDate(String start, String end) {
        List<String> list = new ArrayList<String>();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        Stream.iterate(startDate, d -> {
            return d.plusDays(1);
        }).limit(distance + 1).forEach(f -> {
            list.add(f.toString());
        });
        return list;
    }


}