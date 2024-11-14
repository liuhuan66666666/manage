package com.liuhuan.manage.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 竞赛学生表
 * @TableName student
 */
@TableName(value ="student")
@Data
public class Student implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 学生学号
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 二级学院
     */
    private String college;

    /**
     * 专业名称
     */
    private String major;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 学生状态 (0: inactive - 毕业或离校, 1: active - 在校)，默认在校
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}