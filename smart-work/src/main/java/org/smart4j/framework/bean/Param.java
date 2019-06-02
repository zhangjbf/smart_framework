package org.smart4j.framework.bean;

import java.util.Map;

import org.smart4j.framework.util.CastUtil;

/**
 * 请求参数
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public String getString(String name) {
        return CastUtil.castString(paramMap.get(name));
    }
}
