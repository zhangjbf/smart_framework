package org.smart4j.chapter.service;

import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.bean.Param;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
@Service
public class TestService {

    public String sayHello(Param param) {
        System.out.println("hello " + param.getString("name") + ",how are you");
        return "hello " + param.getString("name") + ",how are you";
    }

}
