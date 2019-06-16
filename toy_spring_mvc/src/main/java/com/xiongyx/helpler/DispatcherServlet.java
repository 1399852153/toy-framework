package com.xiongyx.helpler;

import com.xiongyx.HelperLoader;
import com.xiongyx.annotation.MyController;
import com.xiongyx.helper.BeanFactory;
import com.xiongyx.helper.ClassHelper;
import com.xiongyx.helper.ConfigHelper;
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

//        // 获得ServletContext对象
//        ServletContext servletContext = servletConfig.getServletContext();

        // 注册处理jsp的servlet
//        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
//        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        // 注册处理静态资源的servlet
//        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
//        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }
}
