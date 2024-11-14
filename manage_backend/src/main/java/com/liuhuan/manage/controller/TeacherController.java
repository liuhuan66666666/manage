package com.liuhuan.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuhuan.manage.common.BaseResponse;
import com.liuhuan.manage.common.DeleteRequest;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.common.ResultUtils;
import com.liuhuan.manage.exception.BusinessException;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.model.dto.teacher.TeacherAddRequest;
import com.liuhuan.manage.model.dto.teacher.TeacherEditRequest;
import com.liuhuan.manage.model.dto.teacher.TeacherQueryRequest;
import com.liuhuan.manage.model.dto.teacher.TeacherUpdateRequest;
import com.liuhuan.manage.model.entity.Teacher;
import com.liuhuan.manage.model.vo.TeacherVO;
import com.liuhuan.manage.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 教师表接口
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@RestController
@RequestMapping("/teacher")
@Slf4j
public class TeacherController {

    @Resource
    private TeacherService teacherService;


    // region 增删改查

    /**
     * 创建教师表
     *
     * @param teacherAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addTeacher(@RequestBody TeacherAddRequest teacherAddRequest) {
        ThrowUtils.throwIf(teacherAddRequest == null, ErrorCode.PARAMS_ERROR);
        // todo 在此处将实体类和 DTO 进行转换
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherAddRequest, teacher);
        // 数据校验
        teacherService.validTeacher(teacher, true);
        // 写入数据库
        boolean result = teacherService.save(teacher);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newTeacherId = teacher.getId();
        return ResultUtils.success(newTeacherId);
    }

    /**
     * 删除教师表
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTeacher(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        Teacher oldTeacher = teacherService.getById(id);
        ThrowUtils.throwIf(oldTeacher == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = teacherService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新教师表（仅管理员可用）
     *
     * @param teacherUpdateRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateTeacher(@RequestBody TeacherUpdateRequest teacherUpdateRequest) {
        if (teacherUpdateRequest == null || teacherUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherUpdateRequest, teacher);
        // 数据校验
        teacherService.validTeacher(teacher, false);
        // 判断是否存在
        long id = teacherUpdateRequest.getId();
        Teacher oldTeacher = teacherService.getById(id);
        ThrowUtils.throwIf(oldTeacher == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = teacherService.updateById(teacher);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取教师表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<TeacherVO> getTeacherVOById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Teacher teacher = teacherService.getById(id);
        ThrowUtils.throwIf(teacher == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(teacherService.getTeacherVO(teacher));
    }

    /**
     * 分页获取教师表列表（仅管理员可用）
     *
     * @param teacherQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Teacher>> listTeacherByPage(@RequestBody TeacherQueryRequest teacherQueryRequest) {
        long current = teacherQueryRequest.getCurrentPage();
        long size = teacherQueryRequest.getPageSize();
        // 查询数据库
        Page<Teacher> teacherPage = teacherService.page(new Page<>(current, size),
                teacherService.getQueryWrapper(teacherQueryRequest));
        return ResultUtils.success(teacherPage);
    }

    /**
     * 分页获取教师表列表（封装类）
     *
     * @param teacherQueryRequest
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<TeacherVO>> listTeacherVOByPage(@RequestBody TeacherQueryRequest teacherQueryRequest) {
        long current = teacherQueryRequest.getCurrentPage();
        long size = teacherQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<Teacher> teacherPage = teacherService.page(new Page<>(current, size),
                teacherService.getQueryWrapper(teacherQueryRequest));
        // 获取封装类
        return ResultUtils.success(teacherService.getTeacherVOPage(teacherPage));
    }

    /**
     * 编辑教师表（给用户使用）
     *
     * @param teacherEditRequest
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editTeacher(@RequestBody TeacherEditRequest teacherEditRequest) {
        if (teacherEditRequest == null || teacherEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherEditRequest, teacher);
        // 数据校验
        teacherService.validTeacher(teacher, false);
        // 判断是否存在
        long id = teacherEditRequest.getId();
        Teacher oldTeacher = teacherService.getById(id);
        ThrowUtils.throwIf(oldTeacher == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = teacherService.updateById(teacher);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion
    /**
     * 获取全部教师姓名列表
     *
     */
    @GetMapping("/getTeacherNames")
    public BaseResponse<List<String>> getTeacherNames(){
        ArrayList<String> teacherArrayList = new ArrayList<>();
        for (Teacher teacher : teacherService.list()) {
            teacherArrayList.add(teacher.getName());
        }
        return ResultUtils.success(teacherArrayList);
    }

}
