package com.liuhuan.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhuan.manage.model.dto.department.DepartmentQueryRequest;
import com.liuhuan.manage.model.entity.Department;
import com.liuhuan.manage.model.vo.DepartmentVO;

/**
 * 部门表服务
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
public interface DepartmentService extends IService<Department> {

    /**
     * 校验数据
     *
     * @param department
     * @param add 对创建的数据进行校验
     */
    void validDepartment(Department department, boolean add);

    /**
     * 获取查询条件
     *
     * @param departmentQueryRequest
     * @return
     */
    QueryWrapper<Department> getQueryWrapper(DepartmentQueryRequest departmentQueryRequest);
    
    /**
     * 获取部门表封装
     *
     * @param department
     * @return
     */
    DepartmentVO getDepartmentVO(Department department);


}
