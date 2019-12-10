package com.example.serverdemo.base.entity;

import com.example.serverdemo.base.util.BaseUtil;

/**
 * @description   : 实体抽象类
 * @method_name   :
 * @param         : 
 * @return        :   
 * @throws        : 
 * @date          : 2019/12/10 16:33
 * @author        : Lujw
 * @update date   :
 * @update author :
 */
public abstract class AbstractEntity implements Entity{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3922123931186043789L;

    /**
     * 将实体类转换成XML
     * @return String
     */
    public String toXml() {

        return BaseUtil.toXml(this);

    }

    /**
     * 将实体类转换成JSON
     * @return String
     */
    public String toJSON() {

        return BaseUtil.toJSON(this);

    }

    /**
     * 对象转字符串
     * @return String
     */
    public String toString() {
        return toJSON();
    }

    /**
     * 将实体类进行clone
     * @return Object
     */
    public Object clone() {
        return BaseUtil.serializaClone(this);

    }
    /**
     * Implementation of the hashCode method conforming to the Bloch pattern
     * with the exception of array properties (these are very unlikely primary
     * key types).
     *
     * @return int
     */
    public int hashCode() {

        int result = 17;
//		int idValue = this.getId() == null ? 0 : this.getId().hashCode();
//		result = result * 37 + idValue;
        return result;

    }
    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     *
     * @param obj Object
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj.getClass().equals(this.getClass())) {
            if (obj.hashCode() == hashCode())
                return true;
            return false;
        }
        return false;
    }
}
