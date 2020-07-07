package com.conflux.contract.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author xiaolong
 * @description: 配置mybatis的分页插件pageHelper
 * @date 2019-05-03-01:07
 */
@Component
public class PageHelperConfig {

    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        /**
         * 默认false
         * 设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用和startPage中的pageNum效果一样
         */
        properties.setProperty("offsetAsPageNum","true");
        /**
         * 该参数默认为false 设置为true时，使用RowBounds分页会进行count查询
         */
        properties.setProperty("rowBoundsWithCount","true");
        /**
         * 默认false 启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
         * 禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据
         */
        properties.setProperty("reasonable","true");
        /**
         * 配置mysql数据库的方言
         */
        properties.setProperty("dialect","mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
