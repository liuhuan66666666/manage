package com.liuhuan.manage.model.vo;

import com.liuhuan.manage.model.entity.Department;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 部门表视图
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class DepartmentVO implements Serializable {

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

    /**
     * 封装类转对象
     *
     * @param departmentVO
     * @return
     */
    public static Department voToObj(DepartmentVO departmentVO) {
        if (departmentVO == null) {
            return null;
        }
        Department department = new Department();
        BeanUtils.copyProperties(departmentVO, department);;
        return department;
    }

    /**
     * 对象转封装类
     *
     * @param department
     * @return
     */
    public static DepartmentVO objToVo(Department department) {
        if (department == null) {
            return null;
        }
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(department, departmentVO);
        return departmentVO;
    }
}
