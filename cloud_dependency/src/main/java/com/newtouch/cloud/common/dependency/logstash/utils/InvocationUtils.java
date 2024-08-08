package com.newtouch.cloud.common.dependency.logstash.utils;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author FallenRunning(TBH)
 * 通过invocation获取参数
 */
public class InvocationUtils {

    /**
     * 获取目标方法名
     *
     * @param invocation 目标
     * @return 方法名
     */
    public static String getMethodName(MethodInvocation invocation) {
        return Objects.isNull(invocation) ? null : invocation.getMethod().getName();
    }

    /**
     * @param invocation 目标
     * @return 正在执行的类名(全限定类名)
     */
    public static String getClassName(MethodInvocation invocation) {
        return Objects.isNull(invocation) ? null : Objects.requireNonNull(invocation.getThis()).getClass().getName();
    }

    /**
     * @param invocation 目标
     * @return 正在执行的类名(全限定类名).方法名
     */
    public static String getClassNameAndMethodName(MethodInvocation invocation) {
        if (Objects.isNull(invocation)) {
            return null;
        }
        return getClassName(invocation) + "." + getMethodName(invocation);
    }

    /**
     * @param invocation     目标
     * @param annotationType 注解类型
     * @return 获取当前执行方法的注解实例
     */
    public static <A extends Annotation> A getMethodAnnotation(MethodInvocation invocation, Class<A> annotationType) {
        if (Objects.isNull(invocation) || Objects.isNull(annotationType)) {
            return null;
        }
        Method method = invocation.getMethod();
        return method.getAnnotation(annotationType);
    }

    /**
     *
     * @param invocation 目标
     * @return 方法参数
     */
    public static Object[] getArgs(MethodInvocation invocation){
        return Objects.isNull(invocation)? null : invocation.getArguments();
    }

}
