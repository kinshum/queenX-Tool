package com.queen.core.transaction.config;

import com.queen.core.launch.props.QueenPropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Seata配置类
 *
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@QueenPropertySource(value = "classpath:/queen-transaction.yml")
public class TransactionConfiguration {

}
