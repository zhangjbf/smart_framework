package org.smart4j.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;

/**
 * 主要是处理 请求（Request）与对应的处理器(Handler)的关系
 *
 * 将请求封装，将处理器封装，用map来构建两者的关系
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public class ControllerHelper {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    /**
     * 1、获取所有的controller类
     * 2、获取的controller类中的Action注解的方法
     * 3、将根据Action注解构建Request对象，根据Controller类和method方法构建Handler对象
     * 4、put到ACTION_MAP中去
     */
    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (null != controllerClassSet && controllerClassSet.size() > 0) {
            for (Class<?> clazz : controllerClassSet) {
                Method[] methods = clazz.getMethods();
                if (null != methods && methods.length > 0) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            Action annotation = method.getAnnotation(Action.class);
                            String requestMethod = annotation.requestMethod();
                            String requestPath = annotation.requestPath();
                            ACTION_MAP.put(new Request(requestMethod, requestPath), new Handler(clazz, method));
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据请求方法和路径获取对应的处理器（Handler）
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        return ACTION_MAP.get(new Request(requestMethod, requestPath));
    }
}
