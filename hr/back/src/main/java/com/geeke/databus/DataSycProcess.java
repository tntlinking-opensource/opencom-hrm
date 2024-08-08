package com.geeke.databus;

import cn.hutool.core.lang.ClassScanner;
import com.geeke.common.persistence.DataEntity;
import com.geeke.common.service.CrudService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class DataSycProcess {
    private static final Map<String, CrudService> SERVICE_MAP = new HashedMap();
    private static final Map<String, Class> ENTITY_MAP = new HashedMap();

    @Autowired
    public DataSycProcess(List<CrudService> crudServices) {
        SERVICE_MAP.putAll(crudServices
                .stream()
                .collect(Collectors.toMap(x ->
                        x.getClass().getSimpleName().split("\\$")[0].toLowerCase()
                        , Function.identity())));

        // 处理获取到的子类
        ENTITY_MAP.putAll(getEntityClasses().stream()
                .collect(Collectors.toMap(
                        x -> x.getSimpleName().split("\\$")[0].toLowerCase(), Function.identity())));

    }

    public static void setEntityMap(String key, Class<? extends DataEntity> entity) {
        ENTITY_MAP.put(key.toLowerCase(), entity);
    }

    public static Class<DataEntity> getDataEntity(String key) {
        return ENTITY_MAP.get(key.toLowerCase());
    }

    public static CrudService getService(String key) {
        return SERVICE_MAP.get(key);
    }
    public static CrudService getService(Class entityClass) {
        return SERVICE_MAP.get((entityClass.getSimpleName()+"Service").toLowerCase());
    }

    /**
     * 只扫描能为 com.geeke.*.entity 且继承DataEntity的类
     * @return
     */
    private List<Class<?>> getEntityClasses() {
        String packageName = "com.geeke";
        Class<?> superClass = DataEntity.class;
        List<Class<?>> classes = filterPackageNames(packageName);
        return scanForSubclasses(classes, superClass);
    }

    public List<Class<?>> scanForSubclasses( List<Class<?>> classList, Class<?> superClass) {
        List<Class<?>> subclasses = new ArrayList<>();
        for (Class<?> clazz : classList) {
            if (superClass.isAssignableFrom(clazz)) {
                subclasses.add(clazz);
            }
        }
        return subclasses;
    }

    public List<Class<?>> filterPackageNames(String packageName) {
        Set<Class<?>> packageNames = ClassScanner.scanPackage(packageName);
        List<Class<?>> filteredPackageNames = new ArrayList<>();

        for (Class<?> packageNameTemp : packageNames) {
            String[] split = packageNameTemp.getName().split("\\.");

            if (split.length == 5 && "entity".equals(split[3])) {
                filteredPackageNames.add(packageNameTemp);
            }
        }

        return filteredPackageNames;
    }


}
