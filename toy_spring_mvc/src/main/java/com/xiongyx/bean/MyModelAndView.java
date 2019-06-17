package com.xiongyx.bean;

/**
 * @author xiongyx
 * on 2019/6/16.
 */
public class MyModelAndView extends MyModel{

    /**
     * 返回的视图名称
     * */
    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
