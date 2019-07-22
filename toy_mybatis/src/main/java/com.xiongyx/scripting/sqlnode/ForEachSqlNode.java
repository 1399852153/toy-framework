package com.xiongyx.scripting.sqlnode;

import com.xiongyx.parsing.GenericTokenParser;

import java.util.Map;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * ForEach节点
 */
public class ForEachSqlNode implements SqlNode{

    private static final String ITEM_PREFIX = "__frch_";

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
        // 解析出 foreach collection对应的Iterable迭代对象
        final Iterable<?> iterable = DynamicContextOgnlEvaluator.evaluateIterable(collectionExpression, context);
        if (!iterable.iterator().hasNext()) {
            // 迭代对象为空，不处理直接返回
            return;
        }

        // 添加open
        if (open != null) {
            context.appendSql(open);
        }
        boolean first = true;
        // 开始解析
        int i = 0;
        for (Object o : iterable) {
            DynamicSqlParseContext oldContext = context;
            if (first) {
                context = new PrefixedContext(context, "");
            } else if (separator != null) {
                context = new PrefixedContext(context, separator);
            } else {
                context = new PrefixedContext(context, "");
            }
            int uniqueNumber = context.getUniqueNumber();
            if (o instanceof Map.Entry) {
                @SuppressWarnings("unchecked")
                Map.Entry<Object, Object> mapEntry = (Map.Entry<Object, Object>) o;
                // map collection对象的 index=key
                applyIndex(context, mapEntry.getKey(), uniqueNumber);
                // map collection对象的 item=key
                applyItem(context, mapEntry.getValue(), uniqueNumber);
            } else {
                // map collection对象的 index=list.index
                applyIndex(context, i, uniqueNumber);
                // map collection对象的 item=list[index]
                applyItem(context, o, uniqueNumber);
            }
            // 递归处理
            contents.apply(new FilteredDynamicContext(context, index, item, uniqueNumber));
            if (first) {
                first = !((PrefixedContext) context).isPrefixApplied();
            }
            context = oldContext;
            i++;
        }

        // 添加close
        if (close != null) {
            context.appendSql(close);
        }
    }

    private void applyIndex(DynamicSqlParseContext context, Object o, int i) {
        if (index != null) {
            context.bind(index, o);
            context.bind(itemizeItem(index, i), o);
        }
    }

    private void applyItem(DynamicSqlParseContext context, Object o, int i) {
        if (item != null) {
            context.bind(item, o);
            context.bind(itemizeItem(item, i), o);
        }
    }

    private static String itemizeItem(String item, int i) {
        return ITEM_PREFIX + item + "_" + i;
    }

    private static class FilteredDynamicContext extends DynamicSqlParseContext {
        private DynamicSqlParseContext delegate;
        private int index;
        private String itemIndex;
        private String item;

        FilteredDynamicContext(DynamicSqlParseContext delegate, String itemIndex, String item, int i) {
            super(delegate.getParamObject());
            this.delegate = delegate;
            this.index = i;
            this.itemIndex = itemIndex;
            this.item = item;
        }

        @Override
        public Map<String, Object> getBindings() {
            return delegate.getBindings();
        }

        @Override
        public void bind(String name, Object value) {
            delegate.bind(name, value);
        }

        @Override
        public StringBuilder getSqlBuilder() {
            return delegate.getSqlBuilder();
        }

        @Override
        public void appendSql(String sql) {
            GenericTokenParser parser = new GenericTokenParser("#{", "}", content -> {
                String newContent = content.replaceFirst("^\\s*" + item + "(?![^.,:\\s])", itemizeItem(item, index));
                if (itemIndex != null && newContent.equals(content)) {
                    newContent = content.replaceFirst("^\\s*" + itemIndex + "(?![^.,:\\s])", itemizeItem(itemIndex, index));
                }
                return "#{" + newContent + "}";
            });

            delegate.appendSql(parser.parse(sql));
        }

        @Override
        public int getUniqueNumber() {
            return delegate.getUniqueNumber();
        }

    }

    private class PrefixedContext extends DynamicSqlParseContext {
        private DynamicSqlParseContext delegate;
        private String prefix;
        private boolean prefixApplied;

        PrefixedContext(DynamicSqlParseContext delegate, String prefix) {
            super(delegate.getParamObject());
            this.delegate = delegate;
            this.prefix = prefix;
            this.prefixApplied = false;
        }

        boolean isPrefixApplied() {
            return prefixApplied;
        }

        @Override
        public Map<String, Object> getBindings() {
            return delegate.getBindings();
        }

        @Override
        public void bind(String name, Object value) {
            delegate.bind(name, value);
        }

        @Override
        public void appendSql(String sql) {
            if (!prefixApplied && sql != null && sql.trim().length() > 0) {
                delegate.appendSql(prefix);
                prefixApplied = true;
            }
            delegate.appendSql(sql);
        }

        @Override
        public StringBuilder getSqlBuilder() {
            return delegate.getSqlBuilder();
        }

        @Override
        public int getUniqueNumber() {
            return delegate.getUniqueNumber();
        }
    }
}
