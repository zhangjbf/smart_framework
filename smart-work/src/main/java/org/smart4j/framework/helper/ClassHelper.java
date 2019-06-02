package org.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.util.ClassUtil;

/**
 * 根据根路径获取所有的类
 *
 * 并且获取注解对应的类
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public class ClassHelper {
    /**
     * 用于存放所加载的类
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        //加载所有类
        String appBasePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(appBasePackage);
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取所有注解为service的类
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Service.class)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取所有注解为controller的类
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取所有Bean
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }

    /**
     * 获应用包下某父类的所有子类
     * 
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (superClass.isAssignableFrom(clazz) && !superClass.equals(clazz)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获应用包下某注解的所有类
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                classSet.add(clazz);
            }
        }
        return classSet;
    }
}
