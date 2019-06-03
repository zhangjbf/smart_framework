package org.smart4j.framework.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.smart4j.framework.ds.DataSourceFactory;
import org.smart4j.framework.ds.impl.DefaultDataSourceFactory;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/3
 */
public class DataSourceHelper {

    private static final DataSourceFactory       dataSourceFactory = new DefaultDataSourceFactory();

    private static final ThreadLocal<Connection> conConnection     = new ThreadLocal<>();

    public static DataSource getDataSource() {
        return dataSourceFactory.getDataSource();
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = conConnection.get();
            if (null == conn) {
                conn = getDataSource().getConnection();
                if (null != conn) {
                    conConnection.set(conn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
        return conn;
    }

    public static void beanTransaction() {
        Connection conn = getConnection();
        if (null != conn) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                conConnection.set(conn);
            }
        }
    }

    public static void commitTransaction() {
        Connection conn = getConnection();
        if (null != conn) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                conConnection.remove();
            }
        }
    }

    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (null != conn) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                conConnection.remove();
            }
        }
    }
}
