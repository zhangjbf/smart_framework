package org.smart4j.chapter.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.proxy.AspectProxy;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
@Aspect(Controller.class)
public class ControllerAspect2 extends AspectProxy {

    @Override
    public void before(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
        System.out.println("ControllerAspect2,this is proxy begin");
        System.out.println(new Date());

    }

    @Override
    public void after(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
        System.out.println("ControllerAspect2,this is proxy after");
        System.out.println(new Date());
    }
}
