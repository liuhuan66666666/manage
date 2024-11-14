package com.liuhuan.manage.model.dto.integral;

import lombok.Data;

import java.io.Serializable;

/**
 * 编辑积分表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class IntegralEditRequest implements Serializable {

    /**
     *
     */
    private Integer id;

    /**
     * 成员积分
     */
    private Integer score;
    private static final long serialVersionUID = 1L;
}