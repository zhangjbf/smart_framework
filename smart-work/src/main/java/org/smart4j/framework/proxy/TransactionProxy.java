package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.dao.DataSourceHelper;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/3
 */
public class TransactionProxy implements Proxy {

    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Boolean flag = FLAG_HOLDER.get();

        Method targetMethod = proxyChain.getTargetMethod();
        if (!flag && targetMethod.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DataSourceHelper.beanTransaction();
                result = proxyChain.doProxyChain();
                DataSourceHelper.commitTransaction();
            } catch (Exception e) {
                DataSourceHelper.rollbackTransaction();
            } finally {
                FLAG_HOLDER.remove();
            }
        }else{
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
