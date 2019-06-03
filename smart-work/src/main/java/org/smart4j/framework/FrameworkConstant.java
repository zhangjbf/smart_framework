package org.smart4j.framework;

/**
 * 提供相关配置的常量
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public interface FrameworkConstant {

    String CONFIG_FILE      = "resource/smart.properties";
    String JDBC_DRIVER      = "jdbc.driver";
    String JDBC_URL         = "jdbc.url";
    String JDBC_USERNAME    = "jdbc.username";
    String JDBC_PASSWORD    = "jdbc.password";

    /**
     * 类基本路径
     */
    String APP_BASE_PACKAGE = "app.base_package";
    /**
     * jsp路径
     */
    String APP_JSP_PATH     = "app.jsp_path";
    /**
     * 静态资源路径
     */
    String APP_ASSET_PATH   = "app.asset_path";

    String UTF_8            = "utf-8";
    /**
     * 上传文件大小
     */
    String APP_UPLOAD_LIMIT = "app.upload_limit";

}
