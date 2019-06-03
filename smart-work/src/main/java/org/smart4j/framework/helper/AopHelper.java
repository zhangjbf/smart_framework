package org.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;
import org.smart4j.framework.proxy.TransactionProxy;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public final class AopHelper {

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            if (null != targetMap && targetMap.size() > 0) {
                for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
                    Class<?> targetClass = entry.getKey();
                    List<Proxy> proxies = entry.getValue();
                    Object proxy = ProxyManager.createProxy(targetClass, proxies);
                    BeanHelper.setBean(targetClass, proxy);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (null != annotation && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        if (CollectionUtils.isNotEmpty(proxyClassSet)) {
            for (Class<?> proxyClass : proxyClassSet) {
                if (proxyClass.isAnnotationPresent(Aspect.class)) {
                    Aspect annotation = proxyClass.getAnnotation(Aspect.class);
                    Set<Class<?>> targetClassSet = createTargetClassSet(annotation);
                    proxyMap.put(proxyClass, targetClassSet);
                }
            }
        }
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getServiceClassSet();
        proxyMap.put(TransactionProxy.class,serviceClassSet);
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws IllegalAccessException,
            InstantiationException {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        if (null != proxyMap && proxyMap.size() > 0) {
            Set<Map.Entry<Class<?>, Set<Class<?>>>> entries = proxyMap.entrySet();
            for (Map.Entry<Class<?>, Set<Class<?>>> entry : entries) {
                Class<?> proxyClass = entry.getKey();
                Set<Class<?>> targetClassSet = entry.getValue();
                if (CollectionUtils.isNotEmpty(targetClassSet)) {
                    for (Class<?> clazz : targetClassSet) {
                        Proxy proxy = (Proxy) proxyClass.newInstance();
                        List<Proxy> proxies = targetMap.get(clazz);
                        if (CollectionUtils.isEmpty(proxies)) {
                            proxies = new ArrayList<>();
                            targetMap.put(clazz, proxies);
                        }
                        proxies.add(proxy);
                    }
                }

            }
        }
        return targetMap;
    }

}
