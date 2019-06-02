package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public abstract class AspectProxy implements Proxy {
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();

        begin();

        try {
            if (intercept(targetClass, targetMethod, methodParams)) {
                before(targetClass, targetMethod, methodParams);
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, methodParams);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            end();
        }
        return result;
    }

    public void end() {
    }

    public void after(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
    }

    public void before(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
    }

    public boolean intercept(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
        return true;
    }

    public void begin() {

    }
}
