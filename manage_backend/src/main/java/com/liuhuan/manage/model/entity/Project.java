package com.liuhuan.manage.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目管理表
 * @TableName project
 */
@TableName(value ="project")
@Data
public class Project implements Serializable {
    /**
     * 项目ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 源代码路径
     */
    private String source_code_path;

    /**
     * 项目状态 (0: not_started - 未启动, 1: in_progress - 进行中, 2: completed - 已完成)，默认进行中
     */
    private Integer status;

    /**
     * 项目启动日期
     */
    @TableField("start_date")

    private Date startDate;

    /**
     * 项目组长学生id
     */
    private Long leader_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}