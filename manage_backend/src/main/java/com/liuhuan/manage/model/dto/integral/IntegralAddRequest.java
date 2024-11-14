package com.liuhuan.manage.model.dto.integral;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建积分表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class IntegralAddRequest implements Serializable {

    /**
     *
     */
    @TableId
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