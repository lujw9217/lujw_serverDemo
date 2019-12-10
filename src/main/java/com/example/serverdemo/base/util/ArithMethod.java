package com.example.serverdemo.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author : Lujw
 * @Class Name   : ArithMethod
 * @Description : TODO
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util
 * @date : 2019/12/10 15:59
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class ArithMethod {

    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    //这个类不能实例化
    private ArithMethod()
    {
        ;
    }
    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2)
    {
        return div(v1,v2,DEF_DIV_SCALE);
    }
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale)
    {
        if(scale<0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 提供精确的小数位四舍五入处理。 保留小数点位数不补全
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v,int scale)
    {
        if(scale<0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = BigDecimal.ONE;
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。保留小数点位数补全
     * @param s1 处理数
     * @param scale 四舍五入保留的小数点位数   值>=1
     * @return String
     */
    public static String round(String s1, int scale){
        double v1 = 0d;
        try{
            StringBuffer dot = new StringBuffer();
            if(0 < scale){
                dot.append(".");
                for(int i=1;i<=scale;i++){
                    dot.append("0");
                }
            }
            v1 = Double.parseDouble(s1);
            //格式化，用0补全小数点位
            DecimalFormat df = new DecimalFormat("#0" + dot.toString());
            return df.format(round(v1, scale));
            //return round(v1, scale) + "";
        }catch(Exception e){
            return s1 + "ERROR" + scale;
        }
    }

    /**
     * 两个数字 字符串的 加法运算
     * @param s1 加数
     * @param s2 被加数
     * @param scale 四舍五入保留的小数点位数   值>=1
     * @return String
     */
    public static String add(String s1, String s2, int scale){
        double v1 = 0d;
        double v2 = 0d;
        try{
            v1 = Double.parseDouble(s1);
            v2 = Double.parseDouble(s2);
            return round(add(v1, v2) + "", scale);
        }catch(Exception e){
            return s1 + "ERROR" + s2;
        }
    }

    /**
     * 两个数字 字符串的 减法运算， 四舍五入
     * @param s1 减数
     * @param s2 被减数
     * @param scale 四舍五入保留的小数点位数   值>=1
     * @return String
     */
    public static String sub(String s1, String s2, int scale){
        double v1 = 0d;
        double v2 = 0d;
        try{
            v1 = Double.parseDouble(s1);
            v2 = Double.parseDouble(s2);
            return round(sub(v1, v2) + "", scale);
        }catch(Exception e){
            return s1 + "ERROR" + s2;
        }
    }

    /**
     * 两个数字 字符串的 乘法运算
     * @param s1 乘数
     * @param s2 被乘数
     * @param scale 四舍五入保留的小数点位数  值>=1
     * @return String
     */
    public static String mul(String s1, String s2, int scale){
        double v1 = 0d;
        double v2 = 0d;
        try{
            v1 = Double.parseDouble(s1);
            v2 = Double.parseDouble(s2);
            return round(mul(v1, v2) + "", scale);
        }catch(Exception e){
            return s1 + "ERROR" + s2;
        }
    }

    /**
     * 两个数字 字符串的 加法运算 四舍五入，取整
     * @param s1 加数
     * @param s2 被加数
     * @return String
     */
    public static String add(String s1, String s2){
        double v1 = 0d;
        double v2 = 0d;
        try{
            v1 = Double.parseDouble(s1);
            v2 = Double.parseDouble(s2);
            return (int)round(add(v1, v2), 0) + "";
        }catch(Exception e){
            return s1 + "ERROR" + s2;
        }
    }

    /**
     * 两个数字 字符串的 乘法运算 四舍五入，取整
     * @param s1 乘数
     * @param s2 被乘数
     * @return String
     */
    public static String mul(String s1, String s2){
        double v1 = 0d;
        double v2 = 0d;
        try{
            v1 = Double.parseDouble(s1);
            v2 = Double.parseDouble(s2);
            return (int)round(mul(v1, v2), 0) + "";
        }catch(Exception e){
            return s1 + "ERROR" + s2;
        }
    }

    /**
     * 两个数字 字符串的 减法运算， 四舍五入，取整
     * @param s1 减数
     * @param s2 被减数
     * @return String
     */
    public static String sub(String s1, String s2){
        double v1 = 0d;
        double v2 = 0d;
        try{
            v1 = Double.parseDouble(s1);
            v2 = Double.parseDouble(s2);
            return (int)round(sub(v1, v2), 0) + "";
        }catch(Exception e){
            return s1 + "ERROR" + s2;
        }
    }
}