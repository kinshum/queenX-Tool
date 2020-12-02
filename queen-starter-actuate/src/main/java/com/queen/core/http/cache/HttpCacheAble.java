package com.queen.core.http.cache;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Http cache
 * cache-control
 * <p>
 * max-age 大于0 时 直接从游览器缓存中 提取
 * max-age 小于或等于0 时 向server 发送http 请求确认 ,该资源是否有修改
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpCacheAble {

    /**
     * 缓存的时间,默认0,单位秒
     *
     * @return {long}
     */
    @AliasFor("maxAge")
    long value();

    /**
     * 缓存的时间,默认0,单位秒
     *
     * @return {long}
     */
    @AliasFor("value")
    long maxAge() default 0;

}
