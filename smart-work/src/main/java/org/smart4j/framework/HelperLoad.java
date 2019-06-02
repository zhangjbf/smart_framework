package org.smart4j.framework;

import org.smart4j.framework.helper.AopHelper;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.IocHelper;
import org.smart4j.framework.util.ClassUtil;

/**
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public final class HelperLoad {

    public static void init() {
        Class<?>[] classList = { ClassHelper.class, BeanHelper.class, AopHelper.class, IocHelper.class,
                                 ControllerHelper.class };
        for (Class<?> clazz : classList) {
            ClassUtil.loadClass(clazz.getName());
        }
    }
}
