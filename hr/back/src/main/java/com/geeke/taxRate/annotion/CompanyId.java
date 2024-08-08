package com.geeke.taxRate.annotion;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { })
@Target({ FIELD, TYPE })
@Retention(RUNTIME)
public @interface CompanyId {
    /**
     * 字段名
     * @return
     */
    String value() default "companyId";
}
