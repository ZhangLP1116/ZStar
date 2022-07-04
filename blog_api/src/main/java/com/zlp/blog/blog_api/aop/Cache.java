package com.zlp.blog.blog_api.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    /**
     * 过期时间
     * @return
     */
    long expire() default 1*60*1000;

    /**
     * 缓存标识
     * @return
     */
    String name() default "";


}
