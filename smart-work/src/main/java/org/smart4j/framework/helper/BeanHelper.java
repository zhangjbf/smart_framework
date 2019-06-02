package org.smart4j.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.smart4j.framework.util.ReflectionUtil;

/**
 * 获取所有的Bean以及bean关系
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public class BeanHelper {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> clazz : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(clazz);
            BEAN_MAP.put(clazz, obj);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (!BEAN_MAP.containsKey(clazz)) {
            return null;
        }
        return (T) BEAN_MAP.get(clazz);
    }

    public static void setBean(Class<?> clazz, Object object) {
        BEAN_MAP.put(clazz, object);
    }

}
