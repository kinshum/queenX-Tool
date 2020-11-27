package com.queen.core.secure.provider;

import com.queen.core.secure.constant.SecureConstant;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 获取客户端详情
 *
 */
@AllArgsConstructor
public class ClientDetailsServiceImpl implements IClientDetailsService {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public IClientDetails loadClientByClientId(String clientId) {
		try {
			return jdbcTemplate.queryForObject(SecureConstant.DEFAULT_SELECT_STATEMENT, new String[]{clientId}, new BeanPropertyRowMapper<>(ClientDetails.class));
		} catch (Exception ex) {
			return null;
		}
	}

}
