package com.geeke.utils;

import cn.hutool.core.bean.DynaBean;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Parameter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author FallenRunning(TBH)
 * 远程调用结果解析
 */
public class RemoteResultResolver {

    public static final String REMOTE_FLAG = "remote-flag";

    /**
     * @param resultData      结果集
     * @param resultJoinField 获取结果集中的字段集合一般是id
     * @param remoteField     远程查询结果的字段
     * @return 封装好的参数
     */
    public static SearchParams param(List<?> resultData, String resultJoinField, String remoteField) {
        SearchParams searchParams = new SearchParams();
        if (CollectionUtils.isEmpty(resultData)) {
            return searchParams;
        }
        List<String> fields = resultData.stream().map(s -> (String) DynaBean.create(s).get(resultJoinField)).collect(Collectors.toList());

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter(remoteField, "in", fields));
        searchParams.setParams(parameters);
        return searchParams;
    }

    /**
     * @param resultData      本地调用返回的结果
     * @param remoteData      远程掉用返回的结果
     * @param resultDataField 本地返回结果要设置的属性名称(静态内部类) eg: 引用租户版本
     * @param resultJoinField 本地参与保存关系的属性名称(存id的名称)   eg: 本地保存的是appVersionId;
     * @return 处理好的结果集
     */
    public static List<?> resultResolver(List<?> resultData, List<Map<String, Object>> remoteData, String resultDataField, String resultJoinField) {
        if (CollectionUtils.isEmpty(resultData)) {
            return remoteData;
        }
        //准备数据,以id为key,远程结果为value进行数据清洗
        Map<String, Map<String, Object>> resultMap = new HashMap<>();
        //开始遍历调用结果
        for (Map<String, Object> objectMap : remoteData) {
            //保存到map中
            resultMap.put((String) objectMap.get("id"), objectMap);
        }
        //开始组装数据
        for (Object obj : resultData) {
            DynaBean bean = DynaBean.create(obj);
            String key = bean.get(resultJoinField);
            bean.set(resultDataField, resultMap.get(key));
        }
        return resultData;
    }

    public static int getRemoteFlag() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getIntHeader(REMOTE_FLAG);
    }
}
