package com.liuhuan.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuhuan.manage.model.dto.teacher.TeacherQueryRequest;
import com.liuhuan.manage.model.entity.Teacher;
import com.liuhuan.manage.model.vo.TeacherVO;

/**
 * 教师表服务
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 校验数据
     *
     * @param teacher
     * @param add 对创建的数据进行校验
     */
    void validTeacher(Teacher teacher, boolean add);

    /**
     * 获取查询条件
     *
     * @param teacherQueryRequest
     * @return
     */
    QueryWrapper<Teacher> getQueryWrapper(TeacherQueryRequest teacherQueryRequest);
    
    /**
     * 获取教师表封装
     *
     * @param teacher
     * @return
     */
    TeacherVO getTeacherVO(Teacher teacher);

    /**
     * 分页获取教师表封装
     *
     * @param teacherPage
     * @return
     */
    Page<TeacherVO> getTeacherVOPage(Page<Teacher> teacherPage);


    /**
     * 获取老师id
     *
     * @param: name
     * @return
     */

    Long getTeacherId(String name);
}
