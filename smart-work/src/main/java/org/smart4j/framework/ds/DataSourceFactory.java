package org.smart4j.framework.ds;

import javax.sql.DataSource;

/**
 * 数据源工程接口
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/3
 */
public interface DataSourceFactory {
    /**
     * 获取数据源
     * 
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/3
     */
    DataSource getDataSource();

}
