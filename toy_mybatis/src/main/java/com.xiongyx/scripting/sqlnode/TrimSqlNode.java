package com.xiongyx.scripting.sqlnode;

import com.xiongyx.scripting.DynamicSqlParseContext;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author xiongyx
 * on 2019/7/20.
 */
public class TrimSqlNode implements SqlNode{

    /** 需要添加的前缀 */
    private String prefix;
    /** 需要重载处理的前缀列表 */
    private List<String> prefixesToOverride;

    /** 需要添加的后缀 */
    private String suffix;
    /** 需要重载处理的后列表 */
    private List<String> suffixesToOverride;

    private SqlNode contents;

    public TrimSqlNode(String prefix, List<String> prefixesToOverride, String suffix, List<String> suffixesToOverride, SqlNode contents) {
        this.prefix = prefix;
        this.prefixesToOverride = prefixesToOverride;
        this.suffix = suffix;
        this.suffixesToOverride = suffixesToOverride;
        this.contents = contents;
    }

    @Override
    public void apply(DynamicSqlParseContext context) {
        // 创建一个新的 代理上下文作用域
        FilteredSqlParseDynamicContext filteredSqlParseDynamicContext = new FilteredSqlParseDynamicContext(context);
        // 将代理作用域传递进去
        contents.apply(filteredSqlParseDynamicContext);

        // 代理作用域处理前后缀
        filteredSqlParseDynamicContext.applyAll();
    }

    private class FilteredSqlParseDynamicContext extends DynamicSqlParseContext {
        private DynamicSqlParseContext delegate;
        private StringBuilder newSql;

        FilteredSqlParseDynamicContext(DynamicSqlParseContext delegate) {
            super(delegate.getParamObject());
            this.delegate = delegate;
            this.newSql = new StringBuilder();
        }

        void applyAll() {
            // 将代理作用域收集的sql trim
            newSql = new StringBuilder(newSql.toString().trim());
            // 统一转为大写
            String trimmedUppercaseSql = newSql.toString().toUpperCase(Locale.ENGLISH);
            if (trimmedUppercaseSql.length() > 0) {
                // 处理前缀
                applyPrefix(newSql, trimmedUppercaseSql);
                // 处理后缀
                applySuffix(newSql, trimmedUppercaseSql);
            }

            // 处理完之后的sql 追加到被代理的原作用域对象上
            delegate.appendSql(newSql.toString());
        }

        @Override
        public Map<String, Object> getBindings() {
            // 获取绑定参数时，获取的是原Context
            return delegate.getBindings();
        }

        @Override
        public void bind(String name, Object value) {
            // 绑定参数时，绑定的是原Context
            delegate.bind(name, value);
        }

        @Override
        public void appendSql(String sql) {
            // 尾部追加sql时，追加的是当前Context
            newSql.append(sql);
        }

        private void applyPrefix(StringBuilder sql, String trimmedUppercaseSql) {
            // 如果需要重载的"前缀列表"存在
            if (prefixesToOverride != null) {
                // 遍历重载列表
                for (String toRemove : prefixesToOverride) {
                    // 如果当前sql前缀匹配
                    if (trimmedUppercaseSql.startsWith(toRemove)) {
                        // 将其删除掉(例如：where节点中的多余的"and")
                        sql.delete(0, toRemove.trim().length());
                        // 匹配删除成功后，直接跳出
                        break;
                    }
                }
            }
            // 如果前缀不为空
            if (prefix != null) {
                // 当前sql头部添加上prefix
                sql.insert(0, " ");
                sql.insert(0, prefix);
            }
        }

        private void applySuffix(StringBuilder sql, String trimmedUppercaseSql) {
            // 如果需要重载的"后缀列表"存在
            if (suffixesToOverride != null) {
                // 遍历重载列表
                for (String toRemove : suffixesToOverride) {
                    // 如果当前sql后缀匹配
                    if (trimmedUppercaseSql.endsWith(toRemove) || trimmedUppercaseSql.endsWith(toRemove.trim())) {
                        int start = sql.length() - toRemove.trim().length();
                        int end = sql.length();
                        // 将其删除掉(例如：set节点中多余的",")
                        sql.delete(start, end);
                        // 匹配删除成功后，直接跳出
                        break;
                    }
                }
            }
            // 如果后缀不为空
            if (suffix != null) {
                // 当前sql尾部添加上suffix
                sql.append(" ");
                sql.append(suffix);
            }
        }

    }
}
