package com.example.serverdemo.base.util.Map;

import java.util.List;
import java.util.Map;

/**
 * @author : Lujw
 * @Class Name   : MapGenerator
 * @Description : TODO
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util
 * @date : 2019/12/10 15:24
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public abstract class MapGenerator<T> {
    private List<T> list;

    abstract public Map<Object, T> map();

    /**
     * @return the list
     * @BareFieldName : list
     */

    public List<T> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<T> list) {
        this.list = list;
    }
}
