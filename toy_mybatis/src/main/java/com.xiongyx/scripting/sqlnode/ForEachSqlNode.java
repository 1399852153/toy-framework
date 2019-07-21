package com.xiongyx.scripting.sqlnode;

import com.xiongyx.util.OgnlUtil;

import java.util.Map;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * ForEach节点
 */
public class ForEachSqlNode implements SqlNode{

    private String collectionExpression;
    private String open;
    private String close;
    private String separator;
    private String item;
    private String index;

    private MixedSqlNode contents;

    public static class Builder{
        private ForEachSqlNode target;

        public Builder(){
            target = new ForEachSqlNode();
        }

        public Builder collectionExpression(String collectionExpression){
            target.collectionExpression = collectionExpression;
            return this;
        }

        public Builder open(String open){
            target.open = open;
            return this;
        }

        public Builder close(String close){
            target.close = close;
            return this;
        }

        public Builder separator(String separator){
            target.separator = separator;
            return this;
        }

        public Builder item(String item){
            target.item = item;
            return this;
        }

        public Builder index(String index){
            target.index = index;
            return this;
        }

        public Builder content(MixedSqlNode content){
            target.contents = content;
            return this;
        }

        public ForEachSqlNode build(){
            return target;
        }
    }

    @Override
    public void apply(DynamicSqlParseContext context) {
        Map<String, Object> bindings = context.getBindings();
        // 解析出 foreach collection对应的Iterable迭代对象
        final Iterable<?> iterable = OgnlUtil.evaluateIterable(collectionExpression, bindings);
        if (!iterable.iterator().hasNext()) {
            // 迭代对象为空，不处理直接返回
            return;
        }

        // todo 解析
    }
}
