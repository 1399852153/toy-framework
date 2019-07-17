package com.xiongyx.mapping.sqlsource;

import com.xiongyx.mapping.BoundSql;
import com.xiongyx.scripting.sqlnode.DynamicSqlParseContext;
import com.xiongyx.scripting.sqlnode.SqlNode;

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
    private SqlNode rootNode;

    public DynamicSqlSource(SqlNode rootNode) {
        this.rootNode = rootNode;
    }

    @Override
    public BoundSql getBoundSql(Object paramObject) {
        DynamicSqlParseContext dynamicSqlParseContext = new DynamicSqlParseContext(paramObject);

        rootNode.apply(dynamicSqlParseContext);

        // todo 构造最终的sql
        String sqlText = dynamicSqlParseContext.getSqlBuilder().toString();

        BoundSql boundSql = new BoundSql(paramObject,sqlText);

        return boundSql;
    }
}
