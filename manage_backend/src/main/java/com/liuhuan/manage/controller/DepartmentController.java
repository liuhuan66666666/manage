package com.liuhuan.manage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuhuan.manage.common.BaseResponse;
import com.liuhuan.manage.common.DeleteRequest;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.common.ResultUtils;
import com.liuhuan.manage.exception.BusinessException;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.model.dto.department.DepartmentAddRequest;
import com.liuhuan.manage.model.dto.department.DepartmentEditRequest;
import com.liuhuan.manage.model.dto.department.DepartmentQueryRequest;
import com.liuhuan.manage.model.entity.Department;
import com.liuhuan.manage.model.vo.DepartmentVO;
import com.liuhuan.manage.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门表接口
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@RestController
@RequestMapping("/department")
@Slf4j
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;


    // region 增删改查

    /**
     * 创建部门表
     *
     * @param departmentAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addDepartment(@RequestBody DepartmentAddRequest departmentAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(departmentAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 将实体类和 DTO 进行转换
        Department department = new Department();
        BeanUtils.copyProperties(departmentAddRequest, department);
        // 数据校验
        departmentService.validDepartment(department, true);
        // 填充默认值
        // 写入数据库
        boolean result = departmentService.save(department);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newDepartmentId = department.getId();
        return ResultUtils.success(newDepartmentId);
    }

    /**
     * 删除部门表
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteDepartment(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        Department oldDepartment = departmentService.getById(id);
        ThrowUtils.throwIf(oldDepartment == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        // 操作数据库
        boolean result = departmentService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }



    /**
     * 根据 id 获取部门表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<DepartmentVO> getDepartmentVOById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Department department = departmentService.getById(id);
        ThrowUtils.throwIf(department == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(departmentService.getDepartmentVO(department));
    }

    @PostMapping("/list/page")
    public BaseResponse<DepartmentVO> getDepartmentVOByName(@RequestBody DepartmentQueryRequest departmentQueryRequest) {

        QueryWrapper<Department> queryWrapper = departmentService.getQueryWrapper(departmentQueryRequest);
        Department department = departmentService.getOne(queryWrapper);
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(department,departmentVO);
        return ResultUtils.success(departmentVO);
    }



    /**
     * 编辑部门表（给用户使用）
     *
     * @param departmentEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editDepartment(@RequestBody DepartmentEditRequest departmentEditRequest, HttpServletRequest request) {
        if (departmentEditRequest == null || departmentEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        Department department = new Department();
        BeanUtils.copyProperties(departmentEditRequest, department);
        // 数据校验
        departmentService.validDepartment(department, false);
        // 判断是否存在
        long id = departmentEditRequest.getId();
        Department Department = departmentService.getById(id);
        ThrowUtils.throwIf(Department == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑

        // 操作数据库
        boolean result = departmentService.updateById(department);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }




    @GetMapping("/getList")
    public BaseResponse<List<DepartmentVO>> getDepartment(){
        List<Department> departmentList = departmentService.list();
        List<DepartmentVO> departmentVOList= departmentList.stream().map((department) -> {
            DepartmentVO departmentVO = new DepartmentVO();
            BeanUtils.copyProperties(department, departmentVO);
            return departmentVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(departmentVOList);
    }

    // endregion
}
