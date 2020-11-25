package com.queen.core.jwt.constant;

/**
 * Jwt常量
 */
public interface JwtConstant {

	/**
	 * 默认key
	 */
	String DEFAULT_SECRET_KEY = "queenxisapowerfulmicroservicearchitectureupgradedandoptimizedfromacommercialproject";

	/**
	 * key安全长度，具体见：https://tools.ietf.org/html/rfc7518#section-3.2
	 */
	int SECRET_KEY_LENGTH = 32;

}
