package com.xiongyx.scripting.sqlnode;

import java.util.Arrays;
import java.util.List;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * Where节点
 */
public class WhereSqlNode extends TrimSqlNode{

    /**
     * 需要进行前缀处理的各种形态的 AND字符串
     * */
    private static final List<String> AND_PREFIX =
            Arrays.asList("AND ","OR ","AND\n", "OR\n", "AND\r", "OR\r", "AND\t", "OR\t");


    public WhereSqlNode(MixedSqlNode contents) {
        // 转化为TrimSqlNode，其前缀为where，需要重载处理的内部前缀列表为"AND"
        super("where",AND_PREFIX,null,null,contents);
    }
}
