package org.smart4j.framework.helper;

import java.util.Properties;

import org.smart4j.framework.FrameworkConstant;
import org.smart4j.framework.util.PropsUtil;

/**
 * 属性文件助手类
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(FrameworkConstant.CONFIG_FILE);

    /**
     * 获取jdbc驱动
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, FrameworkConstant.JDBC_DRIVER);
    }

    /**
     * 获取jdbc URL
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, FrameworkConstant.JDBC_URL);
    }

    /**
     * 获取jdbc username
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static String getJdbcUserName() {
        return PropsUtil.getString(CONFIG_PROPS, FrameworkConstant.JDBC_USERNAME);
    }

    /**
     * 获取JDBC_PASSWORD
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, FrameworkConstant.JDBC_PASSWORD);
    }


    /**
     * APP_BASE_PACKAGE
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, FrameworkConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取APP_JSP_PATH
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, FrameworkConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }

    /**
     * 获取APP_ASSET_PATH
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static String getAppAssetPath() {
        return PropsUtil.getString(CONFIG_PROPS, FrameworkConstant.APP_ASSET_PATH, "/asset/");
    }

}
