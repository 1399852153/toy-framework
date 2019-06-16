package com.xiongyx.helpler;

import com.xiongyx.HelperLoader;
import com.xiongyx.util.ClassUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * @author xiongyx
 * on 2019/6/16.
 */
@WebServlet(urlPatterns = "/*" ,loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig){
        // 加载框架
        HelperLoader.init();

        // 加载ControllerHelper
        ClassUtil.loadClass(ControllerHelper.class.getName(),true);

        // 获得ServletContext对象
        ServletContext servletContext = servletConfig.getServletContext();

        // 注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(MVCConfigHelper.getAppJspPath() + "*");

        // 注册处理静态资源的servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(MVCConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        // 获取请求的路径 + 请求的方式 get/post

        // 从ControllerHelper中获取匹配的method反射调用

        // 根据参数列表(参数注解)注入对应的参数

        super.service(req, res);
    }
}
