## 想法
暴露一些端点，提供一些功能。

1. http-cache

2. RateLimiter

3. ... ...

### 不是用网关，单体应用
拦截器处理，基于 redis 的 cache 时间或者 RateLimiter处理。

结构：serviceName:http-cache:/user/1?queryString If-Modified-Since
结构：serviceName:RateLimiter:/user/1 99

### 使用网关
将端点信息存储到 redis 里，供 网关使用。
结构：serviceName:http-cache:endpoint:/user/{id}  100s

结构：serviceName:RateLimiter:endpoint:/user/{id} 100/s

## RateLimiter Headers
```text
#=============================#===================================================#
# HTTP Header                 # Description                                       #
#=============================#===================================================#
| X-RateLimit-Limit           | Request limit per day / per 5 minutes             |
+-----------------------------+---------------------------------------------------+
| X-RateLimit-Remaining       | The number of requests left for the time window   |
+-----------------------------+---------------------------------------------------+
| X-RateLimit-Reset           | The remaining window before the rate limit resets |
|                             |  in UTC epoch seconds                             |
+-----------------------------+---------------------------------------------------+
```