/**
 * 
 */
package com.xiongyx.executor.parameter;


import com.xiongyx.mapping.BoundSql;
import com.xiongyx.mapping.sqlsource.SqlSource;
import com.xiongyx.parsing.SqlParamConvertHelper;
import com.xiongyx.model.MappedStatement;

import java.sql.PreparedStatement;
import java.util.List;


/**
 * 参数解析器
 * 
 * @author xiongyx
 * @date 2019/7/17
 */
public class DefaultParameterHandler implements ParameterHandler
{

    private Object parameter;
    private MappedStatement mappedStatement;

    public DefaultParameterHandler(Object parameter, MappedStatement mappedStatement) {
        this.parameter = parameter;
        this.mappedStatement = mappedStatement;
    }
    
    /**
     * 将SQL参数设置到PreparedStatement中
     */
    @Override
    public void setParameters(PreparedStatement ps) {
        if(parameter == null){
            return;
        }

        try {
            SqlSource sqlSource = mappedStatement.getSqlSource();
            BoundSql boundSql = sqlSource.getBoundSql(parameter);

            // 解析sqlSource中的#{}，获得其中的值，按照顺序构造一个实参列表
            List realParams = SqlParamConvertHelper.parseSqlParam(boundSql);
            for(int i=0; i<realParams.size(); i++){
                Object realParam = realParams.get(i);
                // 向PreparedStatement注入参数 setObject(index,param),用于顶替?
                ps.setObject(i+1,realParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("parse param error" + e);
        }
    }

}
