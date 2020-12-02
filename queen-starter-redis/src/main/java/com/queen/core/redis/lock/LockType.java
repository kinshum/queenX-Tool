package com.queen.core.redis.lock;

/**
 * 锁类型
 *
 * @author jensen
 */
public enum LockType {
    /**
     * 重入锁
     */
    REENTRANT,
    /**
     * 公平锁
     */
    FAIR
}
