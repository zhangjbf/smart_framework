package org.smart4j.framework.proxy;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public interface Proxy {
    /**
     * 执行代理链
     * 
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
