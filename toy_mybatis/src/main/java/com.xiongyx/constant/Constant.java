/*
 * 文件名：Constant.java 版权：Copyright by www.huawei.com 描述： 修改人：ZTE 修改时间：2019年3月8日 跟踪单号： 修改单号： 修改内容：
 */

package com.xiongyx.constant;

/**
 * 〈一句话功能简述〉
 *
 * @author PLF
 * @date 2019年3月8日
 * @version 1.0
 */

public interface Constant {

    /**
     * SQL类型枚举
     */
    enum SqlType {
        /**
         * 增删改查
         * */
        INSERT("insert"),
        UPDATE("update"),
        DELETE("delete"),
        SELECT("select"),
        DEFAULT("default")
        ;

        private String value;

        SqlType(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

}
