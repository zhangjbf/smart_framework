package org.smart4j.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public class ReflectionUtil {
    /**
     * 实例化bean
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static Object newInstance(Class<?> clazz) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 调用方法
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
