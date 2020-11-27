package com.queen.core.api.crypto.annotation.encrypt;

import com.queen.core.api.crypto.enums.CryptoType;

import java.lang.annotation.*;

/**
 * rsa 加密
 *
 * @see ApiEncrypt
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiEncrypt(CryptoType.RSA)
public @interface ApiEncryptRsa {
}
