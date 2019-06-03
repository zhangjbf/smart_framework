package org.smart4j.framework.ds.impl;

import javax.sql.DataSource;

import org.smart4j.framework.ds.DataSourceFactory;
import org.smart4j.framework.helper.ConfigHelper;

/**
 * 模板方法
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/3
 */
public abstract class AbstractDataSourceFactory<T extends DataSource> implements DataSourceFactory {

    protected final String driver   = ConfigHelper.getString("jdbc.driver");
    protected final String url      = ConfigHelper.getString("jdbc.url");
    protected final String username = ConfigHelper.getString("jdbc.username");
    protected final String password = ConfigHelper.getString("jdbc.password");

    @Override
    public final T getDataSource() {
        /**
         * 1、创建数据源
         */
        T ds = createDataSource();
        /**
         * 2、设置数据源基础属性
         */
        setDriver(ds, driver);
        setUrl(ds, url);
        setUsername(ds, username);
        setPassword(ds, password);
        /**
         * 3、设置高级属性
         */
        setAdvancedConfig(ds);
        return ds;
    }

    protected abstract T createDataSource();

    public abstract void setDriver(T ds, String driver);

    public abstract void setUrl(T ds, String url);

    public abstract void setUsername(T ds, String username);

    public abstract void setPassword(T ds, String password);

    public abstract void setAdvancedConfig(T ds);
}
