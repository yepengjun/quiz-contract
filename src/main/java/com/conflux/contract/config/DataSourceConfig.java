package com.conflux.contract.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author xiaolong
 * @description:
 * @date 2019-05-03-01:07
 */
@Component
@PropertySource("classpath:application.properties")
public class DataSourceConfig {

    @Value("${nftcontract.datasource.jdbc.url}")
    private String url;

    @Value("${nftcontract.datasource.driver}")
    private String drive;

    @Value("${nftcontract.datasource.user}")
    private String user;

    @Value("${nftcontract.datasource.password}")
    private String password;

    @Value("${nftcontract.datasource.maxActive}")
    private Integer maxActive;
    @Value("${nftcontract.datasource.maxWait}")
    private Long maxWait;


    @Bean(name = "mybatisDataSource")
    public DataSource mybatisDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(drive);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        return dataSource;
    }
}
