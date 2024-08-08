package com.geeke.taxRate.filter;


import com.geeke.taxRate.annotion.UserId;
import com.geeke.utils.SessionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class InjectUserInfoInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String companyId = SessionUtils.getUser().getCompanyId();
        String userId = SessionUtils.getUser().getId();
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        Arrays.stream(methodParameters).forEach(item -> {
            Parameter parameter = item.getParameter();
            UserId annotation = parameter.getAnnotation(UserId.class);
            if (annotation != null) {
//                parameter.get
            }
        });
        return true;
    }
}
