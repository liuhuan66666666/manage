package com.liuhuan.manage.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 教师信息表
 * @TableName teacher
 */
@TableName(value ="teacher")
@Data
public class Teacher implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 教师工号
     */
    @TableField("teacher_id")
    private Integer teacherId;

    /**
     * 教师姓名
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
     * 所在系
     */
    private String department;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 教师状态 (0: inactive - 离职, 1: active - 在职)，默认在职
     */
    private Integer status;

    /**
     * 教师类型(0:校内指导老师 1:企业指导老师)
     */
    private Integer teacherType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}