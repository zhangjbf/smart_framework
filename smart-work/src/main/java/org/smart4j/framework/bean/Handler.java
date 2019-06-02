package org.smart4j.framework.bean;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public class Handler {

    private Class<?> controllerClass;

    private Method   method;

    public Handler(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Handler handler = (Handler) o;
        return Objects.equals(controllerClass, handler.controllerClass) && Objects.equals(method, handler.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(controllerClass, method);
    }
}
