package org.samrt4j.framework;

import org.samrt4j.framework.bean.Handler;
import org.samrt4j.framework.bean.Param;
import org.samrt4j.framework.helper.BeanHelper;
import org.samrt4j.framework.helper.ConfigHelper;
import org.samrt4j.framework.helper.ControllerHelper;
import org.samrt4j.framework.util.ArrayUtil;
import org.samrt4j.framework.util.CodecUtil;
import org.samrt4j.framework.util.StreamUtil;
import org.samrt4j.framework.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        HelperLoader.init();

        ServletContext servletContext = config.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求方法和路径
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();

        //获取 Action 处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);

        if (handler != null) {
            //获取 Controller 类型和 Bean 实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            /*
            request.getParameter()
            request.getInputStream()
            request.getReader()
            这 三个方法都是从request对象中得到提交的数据，但是用途不同,要根据<form>表单提交数据的编码方式选择不同的方法。

            HTML中的form表单有一个关键属性 enctype＝application/x-www-form-urlencoded 或multipart/form-data。

            request.getParameter()
            request.getInputStream()
            详情参见 http://www.cnblogs.com/v5hanhan/p/5646054.html

             */

            //创建请求参数对象
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paraValue = request.getParameter(paramName);
                paramMap.put(paramName, paraValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if (StringUtil.isNotEmpty(body)) {
                String[] params = StringUtil.splitString(body, "&");
                if (ArrayUtil.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtil.splitString(param, "=");
                        String paramName = array[0];
                        String paramValue = array[1];
                        paramMap.put(paramName, paramValue);
                    }
                }
            }
            Param param = new Param(paramMap);
            //执行方法

            //处理 Action 方法返回值
        }
    }


}
























