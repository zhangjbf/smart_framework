package org.smart4j.chapter.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.dao.DataSourceHelper;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
@Service
public class TestService {

    @Transaction
    public String sayHello(Param param) {
        String sql = "insert into javaboy(name,password) values('javaboyok','123456a')";
        int i = 0;
        Connection connection = DataSourceHelper.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(),e);
        }
        return i + "";
    }

}
