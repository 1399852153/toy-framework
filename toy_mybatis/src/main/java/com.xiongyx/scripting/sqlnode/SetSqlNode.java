package com.xiongyx.scripting.sqlnode;

import java.util.Collections;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * Set节点
 */
public class SetSqlNode extends TrimSqlNode{

    public SetSqlNode(MixedSqlNode contents) {
        // 转化为TrimSqlNode，其前缀为set，需要重载处理的内部后缀列表为","
        super("set",null,null, Collections.singletonList(","),contents);
    }

}
