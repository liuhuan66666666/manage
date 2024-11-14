package com.liuhuan.manage.model.dto.integral;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新积分表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class IntegralUpdateRequest implements Serializable {

    /**
     * 成员积分
     */
    private Integer score;

    private static final long serialVersionUID = 1L;
}