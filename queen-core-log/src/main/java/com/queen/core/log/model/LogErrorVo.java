package com.queen.core.log.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: LogErrorVo视图实体类
 * @author: shenjian26@hotmail.com
 * @create: 2020-12-01 18:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogErrorVo extends LogError {
    private static final long serialVersionUID = 1L;

    private String strId;

}
