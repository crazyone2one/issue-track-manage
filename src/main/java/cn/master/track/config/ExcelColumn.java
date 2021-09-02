package cn.master.track.config;

import java.lang.annotation.*;

/**
 * @author Created by 11's papa on 2021/08/31
 * @version 1.0.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
    String value() default "";
    int col() default 0;
}
