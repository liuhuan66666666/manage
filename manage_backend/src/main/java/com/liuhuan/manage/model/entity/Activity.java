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
 * @TableName activitiy
 */
@TableName(value ="activitiy")
@Data
public class Activity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Date date;

    /**
     * 
     */
    private String location;

    /**
     * 
     */
    private Date created_at;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}