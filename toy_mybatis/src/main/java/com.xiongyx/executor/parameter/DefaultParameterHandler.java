/**
 * 
 */
package com.xiongyx.executor.parameter;


import com.xiongyx.model.MappedStatement;

import java.sql.PreparedStatement;


/**
 * DefaultParameterHandler.java
 * 
 * @author PLF
 * @date 2019年3月6日
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
     *
     * @param ps
     */
    @Override
    public void setParameters(PreparedStatement ps) {
        if(parameter == null){
            return;
        }

        try {
            String sqlSource = mappedStatement.getSqlSource();
            String paramType = mappedStatement.getParamType();
            Class clazz = Class.forName(paramType);

            // 解析sqlSource中的#{}，获得其中的值，按照顺序构造一个实参列表
            // 向PreparedStatement注入参数 setObject(index,param),用于顶替?

            if (parameter.getClass().isArray()) {
                Object[] params = (Object[])parameter;
                for (int i = 0; i < params.length; i++ ) {
                    //Mapper保证传入参数类型匹配，这里就不做类型转换了
                    ps.setObject(i +1, params[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("parse param error" + e);
        }
    }

}
