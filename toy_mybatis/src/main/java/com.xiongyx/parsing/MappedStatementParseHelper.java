package com.xiongyx.parsing;

import com.xiongyx.mapping.sqlsource.SqlSource;
import org.w3c.dom.Element;

/**
 * @author xiongyx
 * on 2019/7/14.
 *
 * MappedStatement解析器
 */
public class MappedStatementParseHelper {

    /**
     * 解析sql单元，构造SqlSource
     * */
    public static SqlSource parseSqlUnit(Element sqlUnitNode){
        // todo

        // rootNode是一个MixedNode，持有一个 contentsList
        // 获取sqlUnitNode的所有一级孩子节点,判断孩子节点类型
            // 文本节点 ==> TextSqlNode 加入contentsList
            // 非文本节点
                // 根据当前节点的名称决定对应的SqlNodeHandler，handler能够递归的解析目前节点的孩子节点
        // 将最终的 contentsList存入rootNode，并构造对应的SqlSource

        return null;
    }
}
