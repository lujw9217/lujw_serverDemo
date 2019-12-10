package com.example.serverdemo.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.serverdemo.base.constant.SystemConstant;
import com.example.serverdemo.base.util.Map.MapGeneratorImpl;
import com.example.serverdemo.base.vo.BaseVO;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang.SerializationUtils;
import org.apache.xerces.impl.dv.util.Base64;
import org.apache.xml.serialize.OutputFormat;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : Lujw
 * @Class Name   : BaseUtil
 * @Description : 基础工具包类
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util.Map
 * @date : 2019/12/10 15:30
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class BaseUtil {
    /**
     * 空的字符串
     **/
    public static final String EMPTY_STRING = "";
    /**
     * null字符串
     **/
    public static final String NULL_STRING = "null";
    /**
     * 连接字符串
     **/
    public static final String CONNECT_STRING = "_";

    /**
     * @param : [object]
     * @return : boolean
     * @throws :
     * @description : 判断对象是否为空
     * @method_name : objectNotNull
     * @date : 2019/12/10 15:47
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean objectNotNull(Object object) {
        if (null == object) {
            return false;
        }
        return true;
    }

    /**
     * @param : [string]
     * @return : boolean
     * @throws :
     * @description : 判断字符串是否为空
     * @method_name : stringNotNull
     * @date : 2019/12/10 15:47
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean stringNotNull(String string) {
        if ((null == string) || (string.trim().equals(""))) {
            return false;
        }
        return true;
    }

    /**
     * @param : [list]
     * @return : boolean
     * @throws :
     * @description : 判断list集合是否为空
     * @method_name : listNotNull
     * @date : 2019/12/10 15:46
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean listNotNull(List<?> list) {
        if (list != null && list.size() != 0) {
            return true;
        }
        return false;
    }

    /**
     * @param : [map]
     * @return : boolean
     * @throws :
     * @description : 判断 map是否为空
     * @method_name : mapNotNull
     * @date : 2019/12/10 15:46
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean mapNotNull(Map<?, ?> map) {
        if (map != null && !map.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * @param : [string]
     * @return : java.lang.Integer
     * @throws :
     * @description : string 转 int 类型
     * @method_name : stringToInteger
     * @date : 2019/12/10 15:46
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static Integer stringToInteger(String string) throws Exception {

        if (!stringNotNull(string)) {

            return null;
        }
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    /**
     * @param : [string]
     * @return : java.lang.Long
     * @throws :
     * @description : string 转 long 类型
     * @method_name : stringToLong
     * @date : 2019/12/10 15:46
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static Long stringToLong(String string) throws Exception {

        if (!stringNotNull(string)) {

            return null;
        }

        try {

            return Long.valueOf(string);

        } catch (NumberFormatException e) {
            throw e;
        }
    }

    /**
     * @param : [string]
     * @return : java.lang.Short
     * @throws :
     * @description : string 转 short类型
     * @method_name : stringToShort
     * @date : 2019/12/10 15:46
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static Short stringToShort(String string) throws Exception {

        if (!stringNotNull(string)) {

            return null;
        }

        try {

            return Short.valueOf(string);

        } catch (NumberFormatException e) {
            throw e;
        }
    }

    /**
     * @param : [string]
     * @return : java.math.BigDecimal
     * @throws :
     * @description : string 转 bigDecimal类型
     * @method_name : stringToBigDecimal
     * @date : 2019/12/10 15:46
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static BigDecimal stringToBigDecimal(String string) throws Exception {

        if (!stringNotNull(string)) {

            return null;
        }

        try {

            return new BigDecimal(string);

        } catch (NumberFormatException e) {
            throw e;
        }
    }

    /**
     * @param : [integer]
     * @return : java.lang.String
     * @throws :
     * @description : int 转string
     * @method_name : integerToString
     * @date : 2019/12/10 15:45
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String integerToString(Integer integer) {

        if (!objectNotNull(integer)) {

            return null;
        }

        return integer.toString();

    }

    /**
     * @param : [lon]
     * @return : java.lang.String
     * @throws :
     * @description : long 转 string
     * @method_name : longToString
     * @date : 2019/12/10 15:45
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String longToString(Long lon) {

        if (!objectNotNull(lon)) {

            return null;
        }

        return lon.toString();

    }

    /**
     * @param : [shortValue]
     * @return : java.lang.String
     * @throws :
     * @description : short 转string
     * @method_name : shortToString
     * @date : 2019/12/10 15:45
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String shortToString(Short shortValue) {

        if (!objectNotNull(shortValue)) {

            return null;
        }

        return shortValue.toString();
    }

    /**
     * @param : [shortValue]
     * @return : java.lang.String
     * @throws :
     * @description : bigdecimal转string
     * @method_name : shortToString
     * @date : 2019/12/10 15:45
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String bigDecimalToString(BigDecimal bigDecimal) {

        if (!objectNotNull(bigDecimal)) {

            return null;
        }

        return bigDecimal.toString();
    }

    /**
     * @param : [string1, string2]
     * @return : java.lang.String
     * @throws :
     * @description : 取值判断
     * @method_name : isNull
     * @date : 2019/12/10 15:45
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String isNull(String string1, String string2) {

        if (stringNotNull(string1)) {
            return string1;
        } else {
            return string2;
        }
    }

    /**
     * @param : [float1]
     * @return : java.lang.String
     * @throws :
     * @description : float 转 string
     * @method_name : floatToString
     * @date : 2019/12/10 15:45
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String floatToString(Float float1) {

        if (BaseUtil.objectNotNull(float1)) {

            return float1.toString();
        }

        return null;
    }

    /**
     * @param : [string]
     * @return : java.lang.Float
     * @throws :
     * @description : string 转 float
     * @method_name : stringToFloat
     * @date : 2019/12/10 15:44
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static Float stringToFloat(String string) {

        if (!stringNotNull(string)) {

            return null;
        }

        try {

            return new Float(string);

        } catch (NumberFormatException e) {
            throw e;
        }
    }

    /**
     * @param : [set]
     * @return : boolean
     * @throws :
     * @description : 判断set是否为空
     * @method_name : setNotNull
     * @date : 2019/12/10 15:44
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean setNotNull(Set<?> set) {

        if (set != null && set.size() > 0) {

            return true;
        }

        return false;
    }

    /**
     * @param : [string]
     * @return : java.lang.String
     * @throws :
     * @description : 字符串 转大写
     * @method_name : stringToUpperCase
     * @date : 2019/12/10 15:44
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String stringToUpperCase(String string) {

        if ((string != null) && (!string.trim().equals(""))) {

            return string.toUpperCase();
        } else {

            return string;
        }
    }

    /**
     * @param : []
     * @return : java.lang.String
     * @throws :
     * @description : 获取IP地址
     * @method_name : getIpAddress
     * @date : 2019/12/10 15:44
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String getIpAddress() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            if (BaseUtil.objectNotNull(address) && BaseUtil.stringNotNull(address.getHostAddress())) {
                return address.getHostAddress();
            }
        } catch (UnknownHostException e) {

            return null;
        }

        return null;
    }

    /**
     * @param : [integer1, integer2]
     * @return : boolean
     * @throws :
     * @description : 比较两个Integer对象的int值是否相等
     * @method_name : integerEquals
     * @date : 2019/12/10 15:44
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean integerEquals(Integer integer1, Integer integer2) {
        if (integer1 != null && integer2 != null) {
            return integer1.intValue() == integer2;
        } else {
            return false;
        }
    }

    /**
     * @param : [data, flag]
     * @return : java.util.List<java.lang.String>
     * @throws :
     * @description : 按照指定标记分割字符串
     * @method_name : splitString
     * @date : 2019/12/10 15:44
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static List<String> splitString(String data, String flag) {
        List<String> list = null;
        if (!BaseUtil.stringNotNull(data) || !BaseUtil.stringNotNull(flag)) {
            list = new ArrayList<String>();
            return list;
        }
        list = new ArrayList<String>();
        String[] arr = data.split(flag);
        for (int i = 0; i < arr.length; i++) {
            if (BaseUtil.stringNotNull(arr[i])) {
                list.add(arr[i]);
            }
        }

        return list;
    }

    /**
     * @param : [data, remain]
     * @return : java.lang.Double
     * @throws :
     * @description : 数据四舍五入
     * @method_name : doubleToRemain
     * @date : 2019/12/10 15:43
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static Double doubleToRemain(Double data, int remain) {
        BigDecimal bg = new BigDecimal(data);
        double result = bg.setScale(remain, BigDecimal.ROUND_HALF_EVEN).doubleValue();
        return result;
    }

    /**
     * @param : [letter]
     * @return : boolean
     * @throws :
     * @description : 判断一个字符串是否为字母
     * @method_name : isLetter
     * @date : 2019/12/10 15:43
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean isLetter(String letter) {
        String reg = "[a-zA-Z]";
        return letter.matches(reg);

    }

    /**
     * @param : [o]
     * @return : java.lang.String
     * @throws :
     * @description : 把对象转成XML格式字符串
     * @method_name : toXml
     * @date : 2019/12/10 15:43
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String toXml(Object o) {
        JSONObject jsonObject = JSONObject.fromObject(o);

        String xml = new XMLSerializer().write(jsonObject);
        return xml;
    }

    /**
     * @param : [o]
     * @return : java.lang.String
     * @throws :
     * @description : 序列化
     * @method_name : serialize
     * @date : 2019/12/10 15:43
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String serialize(Object o) {
        net.sf.json.JSON jsonObject = JSONSerializer.toJSON(o);
        return jsonObject.toString();

    }

    /**
     * @param : [jsonStr, clazz]
     * @return : T
     * @throws :
     * @description : 反序列化
     * @method_name : deserialize
     * @date : 2019/12/10 15:43
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserialize(String jsonStr, Class<T> clazz) {
//		JSONObject jsonObject = new JSONObject(jsonStr);
//		T object = (T)JSONObject.toBean(jsonObject,clazz);
//		return object;
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        T object = (T) JSONObject.toBean(jsonObject, clazz);
        return object;
    }

    /**
     * @param : [object]
     * @return : java.lang.String
     * @throws :
     * @description : 把对象转换成json
     * @method_name : toJSON
     * @date : 2019/12/10 15:42
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static <T> String toJSON(T object) {
        return JSON.toJSONString(object);
    }

    /**
     * @param : [string, clz]
     * @return : T
     * @throws :
     * @description : json字符串转成Java对象
     * @method_name : toJAVA
     * @date : 2019/12/10 15:42
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static <T> T toJAVA(String string, Class<T> clz) {
        return JSON.parseObject(string, clz);
    }

    /**
     * @param : [obj, clazz]
     * @return : T
     * @throws :
     * @description :  将一个接口返回的对象直接转为另一个对象
     * @method_name : objToJAVA
     * @date : 2019/12/10 15:42
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static <T> T objToJAVA(Object obj, Class<T> clazz) {
        if (!objectNotNull(obj)) {
            return null;
        }
        return JSON.parseObject(toJSON(obj), clazz);
    }

    /**
     * @param : [string, clz]
     * @return : java.util.List<T>
     * @throws :
     * @description : 字符串转java集合
     * @method_name : toList
     * @date : 2019/12/10 15:41
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static <T> List<T> toList(String string, Class<T> clz) {
        return JSONArray.parseArray(string, clz);
    }

    /**
     * @param : [obj, clz]
     * @return : java.util.List<T>
     * @throws :
     * @description : 对象转java集合
     * @method_name : objToList
     * @date : 2019/12/10 15:41
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static <T> List<T> objToList(Object obj, Class<T> clz) {
        return JSONArray.parseArray(toJSON(obj), clz);
    }

    /**
     * @param : [o]
     * @return : java.lang.Object
     * @throws :
     * @description : 用序列化工具类克隆对象
     * @method_name : serializaClone
     * @date : 2019/12/10 15:41
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static Object serializaClone(Serializable o) {
        return SerializationUtils.clone(o);
    }

    /**
     * @param : [t]
     * @return : java.lang.String
     * @throws :
     * @description : 将VO对象转json后base64编码
     * @method_name : encryptVO
     * @date : 2019/12/10 15:40
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static <T extends BaseVO> String encryptVO(T t) {
        if (!BaseUtil.objectNotNull(t)) {
            return null;
        }
        // 转json
        String voJson = BaseUtil.toJSON(t);
        byte[] voBytes;
        try {
            voBytes = voJson.getBytes(SystemConstant.SYS_ENCODING);
            voJson = Base64.encode(voBytes);
            return voJson;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * @param : [voJson, t]
     * @return : T
     * @throws :
     * @description : 将对象base64解编码后转成VO对象
     * @method_name : decodeVOJson
     * @date : 2019/12/10 15:40
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static <T extends BaseVO> T decodeVOJson(String voJson, Class<T> t) {
        byte[] voBytes = Base64.decode(voJson);
        try {
            voJson = new String(voBytes, SystemConstant.SYS_ENCODING);
            T vo = toJAVA(voJson, t);
            return vo;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * @param : [request]
     * @return : boolean
     * @throws :
     * @description : 是否是ajax请求
     * @method_name : isAjaxRequest
     * @date : 2019/12/10 15:40
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(header) ? true : false;
        return isAjax;
    }

    /**
     * @param : [str]
     * @return : boolean
     * @throws :
     * @description : 判断 字符串 是否是数字
     * @method_name : isNumeric
     * @date : 2019/12/10 15:40
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * @param : [request]
     * @return : java.lang.String
     * @throws :
     * @description : 获取访问请求的IP地址
     * @method_name : getIpAddr
     * @date : 2019/12/10 15:39
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * @param : []
     * @return : java.lang.String
     * @throws :
     * @description : 获取系统当前时间戳
     * @method_name : getTimeStamp
     * @date : 2019/12/10 15:39
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * @param : []
     * @return : java.lang.String
     * @throws :
     * @description : 生成32位随机串
     * @method_name : getNonceStr
     * @date : 2019/12/10 15:39
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
    }

    /**
     * @param : []
     * @return : java.lang.String
     * @throws :
     * @description : 产生一个随机数
     * @method_name : getRandomNo
     * @date : 2019/12/10 15:39
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String getRandomNo() {
        int rado = (int) (Math.random() * (10));//产生2个0-9的随机数
        int radt = (int) (Math.random() * (10));
        long now = System.currentTimeMillis();//一个13位的时间戳
        String orderId = String.valueOf(rado) + String.valueOf(radt) + String.valueOf(now);// 订单ID
        return orderId;
    }


    /**
     * @param : [str]
     * @return : boolean
     * @throws :
     * @description : 是否为空
     * @method_name : isEmpty
     * @date : 2019/12/10 15:38
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean isEmpty(String str) {
        if (null == str || NULL_STRING.equalsIgnoreCase(str) || EMPTY_STRING.equals(str)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param : [str]
     * @return : boolean
     * @throws :
     * @description : 是否不为空
     * @method_name : isNotEmpty
     * @date : 2019/12/10 15:38
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean isNotEmpty(String str) {
        if (isEmpty(str)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param : [strs]
     * @return : boolean
     * @throws :
     * @description :是否包含空
     * @method_name : isIncludeEmpty
     * @date : 2019/12/10 15:37
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean isIncludeEmpty(String... strs) {
        if (null == strs) {
            return true;
        }
        for (String str : strs) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param : [strToTrim, symble, trimHead]
     * @return : java.lang.String
     * @throws :
     * @description : 裁剪字符
     * @method_name : trim
     * @date : 2019/12/10 15:37
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String trim(String strToTrim, String symble, boolean trimHead) {

        if (!BaseUtil.stringNotNull(strToTrim)) {
            return "";
        }

        if (strToTrim.equals(symble)) {
            return "";
        }

        String trimedStr = strToTrim;
        int symbleLen = symble.length();

        //去除头部的字符
        if (trimHead) {
            if (strToTrim.startsWith(symble)) {
                trimedStr = trimedStr.substring(symbleLen, trimedStr.length());
            }
        }

        //去除尾部的字符
        if (strToTrim.endsWith(symble)) {
            trimedStr = trimedStr.substring(0, (strToTrim.length() - symbleLen));
        }

        return trimedStr;
    }


    /**
     * @param : [string, regStr]
     * @return : boolean
     * @throws :
     * @description : 校验传入的字符串是否满足某格式
     * @method_name : strReg
     * @date : 2019/12/10 15:37
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean strReg(String string, String regStr) {

        String STR_REG = regStr;
        boolean tem = false;
        Pattern pattern = Pattern.compile(STR_REG);
        Matcher matcher = pattern.matcher(string);
        tem = matcher.matches();
        return tem;
    }

    /**
     * @param : [org, dsts]
     * @return : boolean
     * @throws :
     * @description : 判断某字符串是否包含在数组中
     * @method_name : contains
     * @date : 2019/12/10 15:35
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean contains(String org, String... dsts) {
        if (null == dsts || isEmpty(org)) {
            return false;
        }

        for (String str : dsts) {
            if (org.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param : [i]
     * @return : java.lang.String
     * @throws :
     * @description : 十以内数字转汉字，i=0时，返回‘一’，以此类推，9的时候返回‘零’
     * @method_name : numToCharacter
     * @date : 2019/12/10 15:34
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String numToCharacter(int i) {
        // TODO Auto-generated method stub
        String[] numCharacter = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "零"};
        return numCharacter[i];
    }

    /**
     * @param : [firstStr, anotherStr]
     * @return : boolean
     * @throws :
     * @description : 以字典顺序比较两个字符串的大小 true 表示 firstStr>=anotherStr false 表示小于
     * @method_name : isGteThan
     * @date : 2019/12/10 15:34
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean isGteThan(String firstStr, String anotherStr) {
        return firstStr.compareTo(anotherStr) >= 0;
    }

    /**
     * @param : [firstStr, anotherStr]
     * @return : boolean
     * @throws :
     * @description : 以字典顺序比较两个字符串的大小 true 表示 firstStr>anotherStr false 表示小于等于
     * @method_name : isGreaterThan
     * @date : 2019/12/10 15:34
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static boolean isGreaterThan(String firstStr, String anotherStr) {
        return firstStr.compareTo(anotherStr) > 0;
    }

    /**
     * @param : [key, list]
     * @return : java.util.Map<java.lang.Object,T>
     * @throws :
     * @description : 将list转为Map
     * @method_name : listToMap
     * @date : 2019/12/10 15:34
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static <T> Map<Object, T> listToMap(String key, List<T> list) {
        return new MapGeneratorImpl(key, list).map();
    }

    /**
     * @param : [str]
     * @return : java.lang.Boolean
     * @throws :
     * @description : String转boolean
     * @method_name : StringToBoolean
     * @date : 2019/12/10 15:33
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static Boolean StringToBoolean(String str) {
        return (str != null) && str.equalsIgnoreCase("true");
    }

    /**
     * @param : [filePath]
     * @return : java.lang.String
     * @throws :
     * @description :File转Base64
     * 字符串参数filePath是文件的路径
     * @method_name : encryptToBase64
     * @date : 2019/10/28 10:43
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String encryptToBase64(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return java.util.Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * @param : [base64, filePath]
     * @return : java.lang.String
     * @throws :
     * @description : Base64转File
     * 字符串参数base64指的是文件的Base64值，filePath是指的文件将要保存的位置。
     * @method_name : decryptByBase64
     * @date : 2019/10/28 10:45
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String decryptByBase64(String base64, String filePath) {
        if (base64 == null && filePath == null) {
            return "生成文件失败，请给出相应的数据。";
        }
        try {
            Files.write(Paths.get(filePath), java.util.Base64.getDecoder().decode(base64), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "指定路径下生成文件成功！";
    }

    /**
     * @param : [unformattedXml]
     * @return : java.lang.String
     * @throws :
     * @description : 格式化XML字符串
     * @method_name : format
     * @date : 2019/12/10 20:38
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String format(String unformattedXml) {
        try {
            final Document document = parseXmlFile(unformattedXml);
            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            org.apache.xml.serialize.XMLSerializer serializer = new org.apache.xml.serialize.XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}