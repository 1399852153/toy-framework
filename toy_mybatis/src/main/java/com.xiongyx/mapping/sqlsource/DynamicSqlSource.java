package com.xiongyx.mapping.sqlsource;

import com.xiongyx.mapping.BoundSql;
import com.xiongyx.scripting.DynamicSqlParseContext;
import com.xiongyx.scripting.sqlnode.MixedSqlNode;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * 动态sql
 */
public class DynamicSqlSource implements SqlSource{

    /**
     * sqlNode 根节点
     * */
    private MixedSqlNode rootNode;

    public DynamicSqlSource(MixedSqlNode rootNode) {
        this.rootNode = rootNode;
    }

    @Override
    public BoundSql getBoundSql(Object paramObject) {
        DynamicSqlParseContext dynamicSqlParseContext = new DynamicSqlParseContext(paramObject);

        // 递归进去，绑定参数，拼接sql
        rootNode.apply(dynamicSqlParseContext);

        // 构造最终的sql
        String sqlText = dynamicSqlParseContext.getSqlBuilder().toString();

        return new BoundSql(paramObject,sqlText,dynamicSqlParseContext.getBindings());
    }
}
