package com.liuhuan.manage.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门信息表
 * @TableName departments
 */
@TableName(value ="departments")
@Data
public class Department implements Serializable {
    /**
     * 部门的唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门职责
     */
    private String responsibilities;

    /**
     * 部门负责人姓名
     */
    private String manager;

    /**
     * 部门经理电话
     */
    private String manager_phone;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}