package com.liuhuan.manage.model.dto.department;

import com.liuhuan.manage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询部门表请求
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentQueryRequest extends PageRequest implements Serializable {

    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门负责人姓名
     */
    private String manager;



    private static final long serialVersionUID = 1L;
}