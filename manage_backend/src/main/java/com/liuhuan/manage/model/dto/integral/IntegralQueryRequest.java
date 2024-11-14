package com.liuhuan.manage.model.dto.integral;

import com.liuhuan.manage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询积分表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IntegralQueryRequest extends PageRequest implements Serializable {

    /**
     *
     */
    private Integer id;

    /**
     * 成员id
     */
    private Long memberId;

    /**
     * 成员积分
     */
    private Integer score;

    private static final long serialVersionUID = 1L;
}