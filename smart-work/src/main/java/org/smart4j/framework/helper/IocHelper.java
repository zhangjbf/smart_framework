package org.smart4j.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ReflectionUtil;

/**
 * 主要完成将某个实例注入到另一个实例的属性上
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public class IocHelper {
    /**
     * 1、先获取所有的bean和类型的映射集合
     * 2、便利beanClass所有的field，判断是否有inject注解，如果有，就根据类型找到对应的实例，并将实例设置在field上
     */
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (null != beanMap && beanMap.size() > 0) {
            Set<Map.Entry<Class<?>, Object>> entries = beanMap.entrySet();
            for (Map.Entry<Class<?>, Object> entry : entries) {
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();

                Field[] fields = beanClass.getDeclaredFields();
                if (null != fields && fields.length > 0) {
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Inject.class)) {
                            Class<?> fieldType = field.getType();
                            Object fieldObj = beanMap.get(fieldType);
                            if (null != beanInstance) {
                                ReflectionUtil.setField(beanInstance, field, fieldObj);
                            }
                        }
                    }
                }
            }
        }
    }

}
