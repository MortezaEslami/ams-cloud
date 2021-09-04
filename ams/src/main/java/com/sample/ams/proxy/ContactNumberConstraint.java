package com.sample.ams.proxy;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ContactNumberValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactNumberConstraint {
    String message() default "Mobile is not correct !!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}