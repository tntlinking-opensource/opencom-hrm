package com.geeke.taxRate.aop;

import com.geeke.taxRate.annotion.CompanyId;
import com.geeke.taxRate.annotion.UserId;
import com.geeke.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class InjectUserInfoAop {
    public Logger logger = Logger.getLogger(this.getClass());

    @Pointcut("execution(* com.geeke.taxRate.controller.TaxTypeInfoController.*(..)) || " +
            "execution(* com.geeke.salaryArchives.controller.SalaryArchivesController.save(..))")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        String companyId = SessionUtils.getUser().getCompanyId();
        String userId = SessionUtils.getUser().getId();
        Object[] args = joinPoint.getArgs();
        // setCompanyId
        Arrays.stream(args)
                .forEach(item -> {
                    try {
                        if (item.getClass().getAnnotation(CompanyId.class) != null) {
                            String companyFiledName = item.getClass().getAnnotation(CompanyId.class).value();
                            Method method = item.getClass().getMethod("set" + companyFiledName.toUpperCase().charAt(0) + companyFiledName.substring(1), String.class);
                            method.setAccessible(true);
                            method.invoke(item, companyId);
                        }

                        if (item.getClass().getAnnotation(UserId.class) != null) {
                            String userIdFiledName = item.getClass().getAnnotation(UserId.class).value();
                            Method method = item.getClass().getMethod("set" + userIdFiledName.toUpperCase().charAt(0) + userIdFiledName.substring(1), String.class);
                            method.setAccessible(true);
                            method.invoke(item, userId);
                        }
                    } catch (Exception e) {
                        logger.error("Error to set userId Or CompanyId: "+ e.getMessage());
                    }
                });
    }

}
