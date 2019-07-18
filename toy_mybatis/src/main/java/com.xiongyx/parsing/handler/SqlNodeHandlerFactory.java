package com.xiongyx.parsing.handler;

/**
 * @author xiongyx
 * on 2019/7/18.
 */
public class SqlNodeHandlerFactory {

    public static SqlNodeHandler getSqlNodeHandlerByType(String type){
        switch (type){
            case "if":
                return new IfSqlNodeHandler();
            case "where":
                return new WhereSqlNodeHandler();
            case "forEach":
                return new ForEachSqlNodeHandler();
            case "set":
                return new SetSqlNodeHandler();
            default:
                throw new RuntimeException("unknown sqlNode type");
        }
    }
}
