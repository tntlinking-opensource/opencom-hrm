package com.newtouch.cloud.common.dependency.datasource;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author FallenRunning(TBH)
 */
public class JdbcDataSourceProvider extends AbstractDataSourceProvider implements DynamicDataSourceProvider {
    /**
     * JDBC driver
     */
    private final String driverClassName;
    /**
     * JDBC url 地址
     */
    private final String url;
    /**
     * JDBC 用户名
     */
    private final String username;
    /**
     * JDBC 密码
     */
    private final String password;

    /**
     * sql 执行查询数据源的语句 使用预编译的执行器
     */
    private final String sql;

    /**
     * 参数 预编译执行器补全的参数 条件param 此处为服务名称(spring.application.name)
     */
    private final String applicationName;

    private final Class<? extends DataSource> dataSourceType;


    public JdbcDataSourceProvider(String driverClassName, String url, String username, String password, String sql, String applicationName, Class<? extends DataSource> dataSourceType) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
        this.sql = sql;
        this.applicationName = applicationName;
        this.dataSourceType = dataSourceType;
    }

    @Override
    public Map<String, DataSource> loadDataSources() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            if (!StringUtils.isEmpty(driverClassName)) {
                Class.forName(driverClassName);
            }
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.prepareStatement(sql);
            Map<String, DataSourceProperty> dataSourcePropertiesMap = executeStmt(stmt);
            return createDataSourceMap(dataSourcePropertiesMap);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(conn);
            JdbcUtils.closeStatement(stmt);
        }
        return null;
    }

    /**
     * 执行语句获得数据源参数
     * @param statement 预编译执行器 防止sql注入
     * @return 数据源参数
     */
    protected Map<String, DataSourceProperty> executeStmt(PreparedStatement statement) {
        Map<String, DataSourceProperty> dataSourcePropertiesMap = null;
        ResultSet rs = null;
        try {
            dataSourcePropertiesMap = new HashMap<>();
            //补全参数 防止注入
            statement.setString(1, applicationName);
            rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("tenant_id");
                DataSourceProperty property = new DataSourceProperty();
                property.setDriverClassName(rs.getString("driver"));
                property.setUrl(rs.getString("url"));
                property.setUsername(rs.getString("username"));
                property.setPassword(rs.getString("password"));
                property.setType(dataSourceType);
                dataSourcePropertiesMap.put(name, property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dataSourcePropertiesMap;
    }
}
