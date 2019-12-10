package com.example.serverdemo.base.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : Lujw
 * @Class Name   : XStreamUtil
 * @Description : 	XStream解析xml类(java对象与xml格式互换)
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util
 * @date : 2019/12/10 16:11
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class XStreamUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(XStreamUtil.class);
   /**
    * @description   : 将对象序列化成xml
    * @method_name   : objectToXML
    * @param         : [obj]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 16:22
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static String objectToXML(Object obj) throws Exception {
        // 创建xSream对象
        XStream xStream = new XStream(new DomDriver("UTF-8",
                new XmlFriendlyReplacer("-_", "_")));

        if (BaseUtil.objectNotNull(obj)) {
            xStream.autodetectAnnotations(true);
            return xStream.toXML(obj);
        } else {
            return null;
        }
    }

   /**
    * @description   : 将xml字符串转换成对象
    * @method_name   : xmlToObject
    * @param         : [xmlStr, types]
    * @return        : java.lang.Object
    * @throws        :
    * @date          : 2019/12/10 16:22
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static Object xmlToObject(String xmlStr, Class<?>[] types) throws Exception {
        // 创建xSream对象
        XStream xStream = new XStream(new DomDriver("UTF-8",new XmlFriendlyReplacer("-_", "_"))){@Override
        protected MapperWrapper wrapMapper(MapperWrapper next) {
            return new MapperWrapper(next) {
                @Override
                public boolean shouldSerializeMember(Class definedIn,
                                                     String fieldName) {
                    if (definedIn == Object.class) {
                        try {
                            return this.realClass(fieldName) != null;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
                    return super.shouldSerializeMember(definedIn, fieldName);
                }
            };
        }};

        //XStream xStream = new XStream(new DomDriver("UTF-8",new XmlFriendlyReplacer("-_", "_")));

        if (BaseUtil.stringNotNull(xmlStr)) {
            //在解析xml字符串之前，需考虑xml字符串中有特殊字符造成解析失败的问题
            //在解析xml字符串之前，需要对xml字符串去特殊字符处理
			/*这些错误的发生是由于一些不可见的特殊字符的存在，而这些字符对于XMl文件来说又是非法的，所以XML解析器在解析时会发生异常，官方定义了XML的无效字符分为三段：
			0x00 - 0x08
			0x0b - 0x0c
			0x0e - 0x1f

			解决方法是：在解析之前先把字符串中的这些非法字符过滤掉：
			string.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "")
			*/
            xmlStr = xmlStr.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");

            xStream.autodetectAnnotations(true);
            xStream.processAnnotations(types);
            //xStream.ignoreUnknownElements();
            return xStream.fromXML(xmlStr);
        } else {
            return null;
        }
    }

    public static Object xmlToObjectStaxDriver(String xmlStr, Class<?> [] types){
        XStream xStreamStaxDriver = new XStream(new StaxDriver(new QNameMap()));
        if(BaseUtil.stringNotNull(xmlStr)){
            try{
                xStreamStaxDriver.autodetectAnnotations(true);
                xStreamStaxDriver.processAnnotations(types);
                xStreamStaxDriver.ignoreUnknownElements();
                return xStreamStaxDriver.fromXML(xmlStr);
            }catch(Exception e){
                LOGGER.error("xml转换", "xml转换java对象失败:" + "【" + xmlStr + "】" + e.getMessage(), e);
                return null;
            }

        }else{
            return null;
        }
    }
}