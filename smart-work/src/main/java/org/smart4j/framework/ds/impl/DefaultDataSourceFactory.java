package org.smart4j.framework.ds.impl;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * 默认数据源工厂
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/3
 */
public class DefaultDataSourceFactory extends AbstractDataSourceFactory<BasicDataSource> {

    @Override
    protected BasicDataSource createDataSource() {
        return new BasicDataSource();
    }

    @Override
    public void setDriver(BasicDataSource ds, String driver) {
        ds.setDriverClassName(driver);
    }

    @Override
    public void setUrl(BasicDataSource ds, String url) {
        ds.setUrl(url);
    }

    @Override
    public void setUsername(BasicDataSource ds, String username) {
        ds.setUsername(username);
    }

    @Override
    public void setPassword(BasicDataSource ds, String password) {
        ds.setPassword(password);
    }

    @Override
    public void setAdvancedConfig(BasicDataSource ds) {
        ds.setValidationQuery("select 1 from dual");
    }
}
