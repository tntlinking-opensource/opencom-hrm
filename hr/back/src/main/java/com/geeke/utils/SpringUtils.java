package com.geeke.utils;

import java.io.Serializable;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取pring容器对象的工具类
 */
@Component
public class SpringUtils implements ApplicationContextAware, Serializable {
    private static final long serialVersionUID = 1287908822026887245L;
    
    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
 
    public static <T> T getBean(String beanName) {
        if(context.containsBean(beanName)){
            return (T) context.getBean(beanName);
        }else{
            return null;
        }
    }
}
