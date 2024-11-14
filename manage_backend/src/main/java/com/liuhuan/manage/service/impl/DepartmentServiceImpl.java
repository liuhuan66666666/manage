package com.liuhuan.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.mapper.DepartmentMapper;
import com.liuhuan.manage.model.dto.department.DepartmentQueryRequest;
import com.liuhuan.manage.model.entity.Department;
import com.liuhuan.manage.model.vo.DepartmentVO;
import com.liuhuan.manage.service.DepartmentService;
import com.liuhuan.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 部门表服务实现
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Service
@Slf4j
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Resource
    private UserService userService;

    /**
     * 校验数据
     *
     * @param department
     * @param add        对创建的数据进行校验
     */
    @Override
    public void validDepartment(Department department, boolean add) {
        ThrowUtils.throwIf(department == null, ErrorCode.PARAMS_ERROR);


    }

    /**
     * 获取查询条件
     *
     * @param departmentQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Department> getQueryWrapper(DepartmentQueryRequest departmentQueryRequest) {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        if (departmentQueryRequest == null) {
            return queryWrapper;
        }
        String name = departmentQueryRequest.getName();
        queryWrapper.eq("name",name);
        return queryWrapper;

    }


    /**
     * 获取部门表封装
     *
     * @param department
     * @return
     */
    @Override
    public DepartmentVO getDepartmentVO(Department department) {
        // 对象转封装类
        DepartmentVO departmentVO = DepartmentVO.objToVo(department);
        return departmentVO;
    }


}
