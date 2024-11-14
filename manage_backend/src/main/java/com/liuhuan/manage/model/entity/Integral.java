package com.liuhuan.manage.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName integral
 */
@TableName(value ="integral")
@Data
public class Integral implements Serializable {
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}