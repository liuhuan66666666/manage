package com.liuhuan.manage.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 个人赛信息表
 * @TableName individual_competition
 */
@TableName(value ="individual_competition")
@Data
public class IndividualCompetition implements Serializable {
    /**
     * 竞赛ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 竞赛名称
     */
    private String name;

    /**
     * 竞赛年份
     */
    private Integer year;

    /**
     * 竞赛类型
     */
    private String type;

    /**
     * 竞赛状态 (0: inactive - 已经结束, 1: 进行中 )，默认进行中
     */
    private Integer status;

    /**
     * 报名截止日期
     */
    @TableField("registration_deadline")
    private Date registrationDeadline;

    /**
     * 竞赛结束日期
     */
    @TableField("end_deadline")
    private Date endDeadline;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}