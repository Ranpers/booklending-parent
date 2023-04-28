package pers.yiran.booklending.common;

import java.lang.annotation.*;

/**
 * @author Yiran
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface Access {
    AccessLevel level() default AccessLevel.ALL;
}
