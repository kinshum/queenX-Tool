package com.queen.core.tenant.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.queen.core.tenant.dynamic.DsTenantIdProcessor;

import java.lang.annotation.*;

/**
 * 指定租户动态数据源切换.
 *
 */
@DS(DsTenantIdProcessor.TENANT_ID_KEY)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TenantDS {
}
