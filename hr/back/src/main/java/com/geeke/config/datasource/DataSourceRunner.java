package com.geeke.config.datasource;

import java.util.List;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Parameter;
import com.geeke.utils.SpringUtils;
import com.google.common.collect.Lists;
import com.newtouch.cloud.common.dependency.remote.rest.DynamicRestTemplate;
import com.zaxxer.hikari.HikariDataSource;

@Component
@Slf4j
public class DataSourceRunner implements CommandLineRunner {
    // 应用appKey
    @Value(value = "${spring.application.app-key}")
    private String appKey;

    // 应用版本号
    @Value(value = "${spring.application.version}")
    private String versionNo;

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private DefaultDataSourceCreator dataSourceCreator;
    
    @Autowired
    private DynamicRestTemplate restTemplate;
    
    @Override
    public void run(String... strings) throws Exception {
        log.info(">>>>>>>>>>>>>>>执行加载数据源 <<<<<<<<<<<<<");
        String appId = getAppId(appKey);
        JSONObject version = getAppVersion(appId, appKey, versionNo);

        String driver = version.getJSONObject("dbType").getString("driver");
        
        JSONArray tenantApps = getTenatApps(version.getString("id"));
        
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        for(int i = 0; i < tenantApps.size(); i++) {
            JSONObject tenantApp = tenantApps.getJSONObject(i);            
            String tenantId = tenantApp.getJSONObject("tenant").getString("id");
            
            DataSourceProperty dataSourceProperty = new DataSourceProperty();
            dataSourceProperty.setDriverClassName(driver);
            dataSourceProperty.setUrl(tenantApp.getString("dbUrl"));
            dataSourceProperty.setUsername(tenantApp.getString("dbUsername"));
            dataSourceProperty.setPassword(tenantApp.getString("dbPassword"));
            dataSourceProperty.setType(HikariDataSource.class);
            dataSourceProperty.setPoolName(tenantApp.getJSONObject("tenant").getString("id"));
            
            DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
           
            // 通过Flyway对升级（创建）所有动态数据源的数据库
            Flyway flywayInSpring = SpringUtils.getBean("flyway");
            ClassicConfiguration conf = (ClassicConfiguration)flywayInSpring.getConfiguration();
            conf.setDataSource(dataSource);
            conf.getPlaceholders().put("tenant", tenantId); // 租户参数
            Flyway flyway = new Flyway(conf);
            flyway.migrate();
           
            // PoolName就是我们yaml配置中说的数据源名称
            ds.addDataSource(tenantId, dataSource);
            
        }
        log.info(">>>>>>>>>>>>>>>结束加载数据源 <<<<<<<<<<<<<");
    }
    
    

    private String getAppId(String appKey) throws Exception {
        //准备参数
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("app_key", "=", appKey));
        
        SearchParams searchParams = new SearchParams();
        searchParams.setParams(params);
        JSONObject jsonObject;
        try{
            jsonObject = restTemplate.post(
                "cloud-geeke-devtool", 
                "/micro/microApp/listAll", 
                JSONObject.class, searchParams, null);
        }catch(Exception e) {
            throw new Exception("访问开发者平台cloud-geeke-devtool微服务的/micro/microApp/listAll接口异常："+ e.getMessage());
        }
        // 远程服务访问失败
        if(!"100".equals(jsonObject.getString("code"))) {
            throw new Exception("访问开发者平台cloud-geeke-devtool微服务的/micro/microApp/listAll接口返回错误：" + jsonObject.getString("msg"));
        }
        
        JSONArray apps = jsonObject.getJSONArray("data");
        if(apps.size() == 0) {
            throw new Exception("开发者平台没有找到appKey为" + appKey + "的应用");
        }
        if(apps.size() > 1) {
            throw new Exception("开发者平台appKey为" + appKey + "存在多个应用");
        }
        JSONObject app = apps.getJSONObject(0);
        return app.getString("id");
    }
    
    private JSONObject getAppVersion(String appId, String appKey, String versionNo) throws Exception {
        //准备参数
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("app_id", "=", appId));
        params.add(new Parameter("name", "=", versionNo));
        
        SearchParams searchParams = new SearchParams();
        searchParams.setParams(params);
        JSONObject jsonObject;
        try{
            jsonObject = restTemplate.post(
                "cloud-geeke-devtool", 
                "/micro/microVersion/listAll", 
                JSONObject.class, searchParams, null);
        }catch(Exception e) {
            throw new Exception("访问开发者平台cloud-geeke-devtool微服务的/micro/microVersion/listAll接口异常："+ e.getMessage());
        }
        // 远程服务访问失败
        if(!"100".equals(jsonObject.getString("code"))) {
            throw new Exception("访问开发者平台cloud-geeke-devtool微服务的/micro/microVersion/listAll接口返回错误：" + jsonObject.getString("msg"));
        }
        
        JSONArray versions = jsonObject.getJSONArray("data");
        if(versions.size() == 0) {
            throw new Exception("开发者平台appKey为" + appKey + "的应用没有找到的版本号" + versionNo);
        }
        if(versions.size() > 1) {
            throw new Exception("开发者平台appKey为" + appKey + "的应用版本号" + versionNo + "存在多个");
        }

        return versions.getJSONObject(0);
    }
    
    
    private JSONArray getTenatApps(String versionId) throws Exception {
        //准备参数
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("app_version_id", "=", versionId));
        
        SearchParams searchParams = new SearchParams();
        searchParams.setParams(params);

        JSONObject jsonObject;
        try{
            jsonObject = restTemplate.post(
                "cloud-geeke-organisation", 
                "/ten/tenantApp/listAll", 
                JSONObject.class, searchParams, null);
        }catch(Exception e) {
            throw new Exception("访问组织架构cloud-geeke-organisation微服务的/ten/tenantApp/listAll接口异常："+ e.getMessage());
        }
        // 远程服务访问失败
        if(!"100".equals(jsonObject.getString("code"))) {
            throw new Exception("访问组织架构cloud-geeke-organisation微服务的/ten/tenantApp/listAll接口返回错误：" + jsonObject.getString("msg"));
        }
        
        return jsonObject.getJSONArray("data");
    }
}