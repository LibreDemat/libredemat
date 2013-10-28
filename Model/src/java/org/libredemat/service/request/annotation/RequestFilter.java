package org.libredemat.service.request.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.libredemat.security.annotation.ContextPrivilege;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD })
public @interface RequestFilter {
    
    ContextPrivilege privilege() default ContextPrivilege.READ;
}
