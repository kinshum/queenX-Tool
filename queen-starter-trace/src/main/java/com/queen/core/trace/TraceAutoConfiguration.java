package com.queen.core.trace;

import com.queen.core.launch.props.QueenPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * TraceAutoConfiguration
 *
 */
@Configuration
@QueenPropertySource(value = "classpath:/queen-trace.yml")
public class TraceAutoConfiguration {
}
