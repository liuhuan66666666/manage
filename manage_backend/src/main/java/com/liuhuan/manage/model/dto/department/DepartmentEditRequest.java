package com.liuhuan.manage.model.dto.department;

import lombok.Data;

import java.io.Serializable;

/**
 * 编辑部门表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class DepartmentEditRequest implements Serializable {

    /**
     * 部门的唯一标识
     */
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


    private static final long serialVersionUID = 1L;
}