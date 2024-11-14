package com.liuhuan.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuhuan.manage.common.BaseResponse;
import com.liuhuan.manage.common.DeleteRequest;
import com.liuhuan.manage.common.ErrorCode;
import com.liuhuan.manage.common.ResultUtils;
import com.liuhuan.manage.exception.BusinessException;
import com.liuhuan.manage.exception.ThrowUtils;
import com.liuhuan.manage.model.dto.student.StudentAddRequest;
import com.liuhuan.manage.model.dto.student.StudentEditRequest;
import com.liuhuan.manage.model.dto.student.StudentQueryRequest;
import com.liuhuan.manage.model.dto.student.StudentUpdateRequest;
import com.liuhuan.manage.model.entity.Student;
import com.liuhuan.manage.model.vo.StudentVO;
import com.liuhuan.manage.service.StudentService;
import com.liuhuan.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生表接口
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Resource
    private StudentService studentService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建学生表
     *
     * @param studentAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addStudent(@RequestBody StudentAddRequest studentAddRequest) {
        ThrowUtils.throwIf(studentAddRequest == null, ErrorCode.PARAMS_ERROR);
        // todo 在此处将实体类和 DTO 进行转换
        Student student = new Student();
        BeanUtils.copyProperties(studentAddRequest, student);
        // 数据校验
        studentService.validStudent(student, true);
        // 写入数据库
        boolean result = studentService.save(student);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newStudentId = student.getId();
        return ResultUtils.success(newStudentId);
    }

    /**
     * 删除学生表
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteStudent(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = deleteRequest.getId();
        // 判断是否存在
        Student oldStudent = studentService.getById(id);
        ThrowUtils.throwIf(oldStudent == null, ErrorCode.NOT_FOUND_ERROR);

        // 操作数据库
        boolean result = studentService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新学生表（仅管理员可用）
     *
     * @param studentUpdateRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateStudent(@RequestBody StudentUpdateRequest studentUpdateRequest) {
        if (studentUpdateRequest == null || studentUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        Student student = new Student();
        BeanUtils.copyProperties(studentUpdateRequest, student);
        // 数据校验
        studentService.validStudent(student, false);
        // 判断是否存在
        long id = studentUpdateRequest.getId();
        Student oldStudent = studentService.getById(id);
        ThrowUtils.throwIf(oldStudent == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = studentService.updateById(student);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取学生表（封装类）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<StudentVO> getStudentVOById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Student student = studentService.getById(id);
        ThrowUtils.throwIf(student == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(studentService.getStudentVO(student));
    }

    /**
     * 分页获取学生表列表（仅管理员可用）
     *
     * @param studentQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Student>> listStudentByPage(@RequestBody StudentQueryRequest studentQueryRequest) {
        long current = studentQueryRequest.getCurrentPage();
        long size = studentQueryRequest.getPageSize();
        // 查询数据库
        Page<Student> studentPage = studentService.page(new Page<>(current, size),
                studentService.getQueryWrapper(studentQueryRequest));
        return ResultUtils.success(studentPage);
    }

    /**
     * 分页获取学生表列表（封装类）
     *
     * @param studentQueryRequest
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<StudentVO>> listStudentVOByPage(@RequestBody StudentQueryRequest studentQueryRequest) {
        long current = studentQueryRequest.getCurrentPage();
        long size = studentQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<Student> studentPage = studentService.page(new Page<>(current, size),
                studentService.getQueryWrapper(studentQueryRequest));
        // 获取封装类
        return ResultUtils.success(studentService.getStudentVOPage(studentPage));
    }

    /**
     * 编辑学生表（给用户使用）
     *
     * @param studentEditRequest
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editStudent(@RequestBody StudentEditRequest studentEditRequest) {
        if (studentEditRequest == null || studentEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 在此处将实体类和 DTO 进行转换
        Student student = new Student();
        BeanUtils.copyProperties(studentEditRequest, student);
        // 数据校验
        studentService.validStudent(student, false);

        // 判断是否存在
        long id = studentEditRequest.getId();
        Student oldStudent = studentService.getById(id);
        ThrowUtils.throwIf(oldStudent == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = studentService.updateById(student);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 获取全部学生列表
     *
     */
    @GetMapping("/getStudentNames")
    public BaseResponse<List<String>> getStudentNames(){
        ArrayList<String> studentArrayList = new ArrayList<>();
        for (Student student : studentService.list()) {
            studentArrayList.add(student.getName());
        }
        return ResultUtils.success(studentArrayList);
    }

    // endregion
}
