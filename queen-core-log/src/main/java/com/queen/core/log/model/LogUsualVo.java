package com.queen.core.log.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: LogUsualVo 视图实体类
 * @author: shenjian26@hotmail.com
 * @create: 2020-12-01 17:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogUsualVo extends LogUsual {
    private static final long serialVersionUID = 1L;

    private String strId;

}
