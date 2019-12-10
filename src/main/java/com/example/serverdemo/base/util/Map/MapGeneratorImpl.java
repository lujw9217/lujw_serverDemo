package com.example.serverdemo.base.util.Map;

import com.example.serverdemo.base.util.BaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Lujw
 * @Class Name   : MapGeneratorimpl
 * @Description : TODO
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util.Map
 * @date : 2019/12/10 15:28
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class MapGeneratorImpl<T> extends MapGenerator<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapGeneratorImpl.class);
    //获取Mapkey的属性名
    private String keyField;

    public MapGeneratorImpl(String keyField, List<T> list) {
        this.keyField = keyField;
        setList(list);
    }

    public Map<Object,T> map(){

        Map<Object,T> map = null;
        if(!BaseUtil.stringNotNull(keyField)){
            LOGGER.error("<-- mapUtil,list转Map出错，属性key为空 -->");
            return map;
        }

        if(BaseUtil.listNotNull(getList())){
            map = new HashMap<>();
            Class<?> clazz=getList().get(0).getClass();
            //获取对应属性
            Field field;
            try {
                field = clazz.getDeclaredField(keyField);
                field.setAccessible(true);
                for (T t : getList()){
                    try {
                        map.put(field.get(t),t);
                    } catch (Exception e) {
                        LOGGER.error("<!-- mapUtil,list转Map失败，未找到属性名称为{}的属性值！ --!>",keyField);
                    }
                }
            } catch (NoSuchFieldException e) {
                LOGGER.error("<!-- mapUtil,list转Map失败，未找到属性名称为{}的属性！ --!>",keyField);
            }
        }else{
            LOGGER.error("<!-- mapUtil,list转Map失败，list为空！ --!>");
        }
        return map;
    }
}