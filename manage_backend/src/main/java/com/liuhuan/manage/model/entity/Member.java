package com.liuhuan.manage.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName member
 */
@TableName(value ="member")
@Data
public class Member implements Serializable {
    /**
     *
     /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Integer gender;

    /**
     * 
     */
    private String studentId;

    /**
     * 
     */
    private String contact;

    /**
     * 
     */
    private Date joinDate;

    /**
     * 
     */
    private String department;

    /**
     * 
     */
    private String major;

    /**
     * 
     */
    private String grade;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}