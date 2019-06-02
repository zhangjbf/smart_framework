package org.smart4j.chapter.controller;

import org.smart4j.chapter.service.TestService;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Param;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
@Controller
public class TestController {

    @Inject
    private TestService testService;

    @Action(requestMethod = "get", requestPath = "/hello")
    public Data hello(Param param) {
        String s = testService.sayHello(param);
        Data data = new Data(s);
        return data;
    }

}
