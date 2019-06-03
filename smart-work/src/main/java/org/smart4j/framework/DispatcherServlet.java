package org.smart4j.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.RequestHelper;
import org.smart4j.framework.helper.UploadHelper;
import org.smart4j.framework.util.JsonUtils;
import org.smart4j.framework.util.ReflectionUtil;
import org.smart4j.framework.util.StringUtil;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        /**
         * 初始化框架类
         */
        HelperLoad.init();
        /**
         * 获取servlet上下文
         */
        ServletContext servletContext = servletConfig.getServletContext();
        /**
         * 注册jsp路径
         */
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        /**
         * 注册静态资源路径
         */
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                     IOException {
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();

        if (requestPath.equals("/favicon.ico")) {
            return;
        }

        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (null != handler) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            Param param;
            if (UploadHelper.isMultiPart(request)) {
                param = UploadHelper.createParam(request);
            } else {
                param = RequestHelper.createParam(request);
            }

            Method method = handler.getMethod();
            Object result = null;
            if (param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerBean, method);
            } else {
                result = ReflectionUtil.invokeMethod(controllerBean, method, param);
            }

            if (result instanceof View) {
                handleViewResult((View) result, request, response);
            } else if (result instanceof Data) {
                handleDataResult((Data) result, request, response);
            }
        }
    }

    private void handleDataResult(Data data, HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (null != model) {
            response.setContentType("application/json");
            response.setCharacterEncoding(FrameworkConstant.UTF_8);
            PrintWriter writer = response.getWriter();
            String json = JsonUtils.objectToJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    private void handleViewResult(View view, HttpServletRequest request,
                                  HttpServletResponse response) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                Set<Map.Entry<String, Object>> entries = model.entrySet();
                if (null != entries && entries.size() > 0) {
                    for (Map.Entry<String, Object> entry : entries) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        request.setAttribute(key, value);
                    }
                    request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
                }
            }
        }
    }
}
