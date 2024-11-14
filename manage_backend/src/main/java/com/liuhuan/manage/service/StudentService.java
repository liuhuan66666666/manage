package com.liuhuan.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhuan.manage.model.dto.student.StudentQueryRequest;
import com.liuhuan.manage.model.entity.Student;
import com.liuhuan.manage.model.vo.StudentVO;

/**
 * 学生表服务
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
public interface StudentService extends IService<Student> {

    /**
     * 校验数据
     *
     * @param student
     * @param add 对创建的数据进行校验
     */
    void validStudent(Student student, boolean add);

    /**
     * 获取查询条件
     *
     * @param studentQueryRequest
     * @return
     */
    QueryWrapper<Student> getQueryWrapper(StudentQueryRequest studentQueryRequest);
    
    /**
     * 获取学生表封装
     *
     * @param student
     * @return
     */
    StudentVO getStudentVO(Student student);

    /**
     * 分页获取学生表封装
     *
     * @param studentPage
     * @return
     */
    Page<StudentVO> getStudentVOPage(Page<Student> studentPage);


    /**
     * 获取学生id
     *
     * @param: name
     * @return
     */

    Long getStudentId(String name);
}
